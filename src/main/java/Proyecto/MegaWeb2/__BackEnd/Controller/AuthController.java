package Proyecto.MegaWeb2.__BackEnd.Controller;

import Proyecto.MegaWeb2.__BackEnd.Dto.ResponseLoginDto;
import Proyecto.MegaWeb2.__BackEnd.Security.JwtUtil;
import Proyecto.MegaWeb2.__BackEnd.Service.UsuarioService;
import Proyecto.MegaWeb2.__BackEnd.Service.AuditService;
import Proyecto.MegaWeb2.__BackEnd.Repository.UsuarioRepository;
import Proyecto.MegaWeb2.__BackEnd.Dto.UsuarioDTO;
import Proyecto.MegaWeb2.__BackEnd.Util.URLEncryptionUtil;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import java.time.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import Proyecto.MegaWeb2.__BackEnd.Service.EmailService;
import Proyecto.MegaWeb2.__BackEnd.Service.HashUtil;
import Proyecto.MegaWeb2.__BackEnd.Service.TwoFactorService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TwoFactorService twoFactorService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AuditService auditService;

    private Map<String, Object> createResponse(String status, String message, Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", status);
        map.put("message", message);
        map.put("data", data);
        return map;
    }

    private String obtenerIPCliente() {
        ServletRequestAttributes attrs = 
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) return "UNKNOWN";
        
        HttpServletRequest request = attrs.getRequest();
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        String ipCliente = obtenerIPCliente();

        if (username == null || password == null) {
            // Registrar intento sospechoso
            auditService.registrarLoginFallido("DESCONOCIDO", ipCliente, 
                "Faltan credenciales");
            return ResponseEntity.badRequest()
                .body(createResponse("error", "Faltan datos de login", null));
        }

        UsuarioDTO user = usuarioService.authenticate(username, password);
        
        if (user == null) {
            // ❌ LOGIN FALLIDO - Registrar en auditoría
            auditService.registrarLoginFallido(username, ipCliente, 
                "Credenciales inválidas");
            
            return ResponseEntity.status(401)
                .body(createResponse("error", "Credenciales inválidas", null));
        }

        // ✅ LOGIN EXITOSO - Registrar en auditoría
        auditService.registrarLoginExitoso(username, ipCliente);

        ResponseLoginDto response = new ResponseLoginDto();
        response.setUsername(username);
        response.setRequire2FA(user.isTwoFactorEnabled());

        if (user.isTwoFactorEnabled()) {
            return ResponseEntity.ok(
                createResponse("success", "Se requiere verificación 2FA", response));
        }

        Map<String, Object> provisionalToken = jwtUtil.generateToken(user);
        long expiration = (Long) provisionalToken.get("expiration");
        Date dateTime = new Date(expiration);
        String hashedUsername = HashUtil.hashUsername(username);
        usuarioService.updateExpirationToken(hashedUsername, dateTime);

        response.setExpiration(dateTime);
        response.setToken((String) provisionalToken.get("token"));
        response.setIsFirstAuthGoogle(0);
        
        return ResponseEntity.ok(createResponse("success", "Login exitoso", response));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> recuperarPassword(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String ipCliente = obtenerIPCliente();

        if (email == null || email.isEmpty()) {
            auditService.registrarEvento("ANONIMO", "FORGOT_PASSWORD_FALLIDO", 
                "Email no proporcionado", ipCliente, "/api/auth/forgot-password", "POST");
            
            return ResponseEntity.badRequest()
                .body(createResponse("error", "Falta el correo electrónico", null));
        }

        UsuarioDTO user = usuarioService.findByEmail(email);
        if (user == null) {
            // No revelar que el email no existe (seguridad)
            auditService.registrarEvento("ANONIMO", "FORGOT_PASSWORD", 
                "Email no encontrado: " + email, ipCliente, "/api/auth/forgot-password", "POST");
            
            return ResponseEntity.ok(
                createResponse("success", "Si el email existe, recibirás un enlace", null));
        }

        String token = jwtUtil.generateTemporaryToken(email, Duration.ofMinutes(15));
        String resetLink = "https://megayuntas.amazoncode.dev/reset-password?token=" + token;
        String asunto = "Recupera tu contraseña - MegaWeb";
        String mensaje =
                "Hola,\n\n" +
                "Recibimos una solicitud para restablecer tu contraseña.\n" +
                "Haz clic en el siguiente enlace para crear una nueva contraseña:\n\n" +
                resetLink + "\n\n" +
                "Si no solicitaste este cambio, ignora este correo.\n\n" +
                "El enlace expirará en 15 minutos.";

        emailService.enviarCorreo(email, asunto, mensaje);
        
        // ✅ Registrar intento exitoso
        auditService.registrarEvento(user.getNombres(), "FORGOT_PASSWORD_EXITOSO", 
            "Email de recuperación enviado a: " + email, ipCliente, 
            "/api/auth/forgot-password", "POST");
        
        return ResponseEntity.ok(
            createResponse("success", "Correo de recuperación enviado", null));
    }

    @PostMapping("/restablecer-password")
    public ResponseEntity<?> restablecerPassword(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String nuevaPassword = body.get("password");
        String ipCliente = obtenerIPCliente();

        if (token == null || nuevaPassword == null) {
            auditService.registrarEvento("ANONIMO", "RESET_PASSWORD_FALLIDO", 
                "Datos incompletos", ipCliente, "/api/auth/restablecer-password", "POST");
            
            return ResponseEntity.badRequest()
                .body(createResponse("error", "Datos incompletos", null));
        }

        String email = jwtUtil.validarYObtenerEmail(token);
        if (email == null) {
            auditService.registrarEvento("ANONIMO", "RESET_PASSWORD_FALLIDO", 
                "Token inválido o expirado", ipCliente, "/api/auth/restablecer-password", "POST");
            
            return ResponseEntity.status(400)
                .body(createResponse("error", "Token inválido o expirado", null));
        }

        UsuarioDTO user = usuarioService.findByEmail(email);
        if (user == null) {
            return ResponseEntity.status(404)
                .body(createResponse("error", "Usuario no encontrado", null));
        }

        usuarioService.updatePassword(email, nuevaPassword);
        
        // ✅ Registrar cambio de contraseña exitoso
        auditService.registrarCambioPassword(user.getNombres(), ipCliente);
        
        return ResponseEntity.ok(
            createResponse("success", "Contraseña actualizada correctamente", null));
    }

    @PostMapping("/verify-2fa")
    public ResponseEntity<?> verifyTwoFactor(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        int code = Integer.parseInt(body.get("code"));
        String ipCliente = obtenerIPCliente();

        String hashedUsername = HashUtil.hashUsername(username);
        UsuarioDTO user = usuarioService.findByUsername(hashedUsername);

        if (user == null) {
            auditService.registrarLoginFallido(username, ipCliente, "Usuario no encontrado");
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }

        boolean valid = twoFactorService.verifyCode(user.getSecret2FA(), code);
        if (!valid) {
            auditService.registrarLoginFallido(username, ipCliente, "Código 2FA inválido");
            return ResponseEntity.status(401).body("Código inválido");
        }

        usuarioRepository.updateTwoFactorEnabled(hashedUsername, true);
        
        // ✅ Registrar 2FA exitoso
        auditService.registrarEvento(username, "2FA_EXITOSO", 
            "Verificación 2FA completada", ipCliente, "/api/auth/verify-2fa", "POST");
        
        Map<String, Object> tokenData = jwtUtil.generateToken(user);
        return ResponseEntity.ok(tokenData);
    }

    @GetMapping("/generate-qr/{username}")
    public ResponseEntity<Map<String, Object>> generateQR(@PathVariable String username) {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        GoogleAuthenticatorKey key = gAuth.createCredentials();

        String issuer = "MegaWeb";
        String secret = key.getKey();

        String hashedUsername = HashUtil.hashUsername(username);
        usuarioService.updateSecret2FA(hashedUsername, secret);

        String otpAuthUrl = String.format(
                "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                issuer, username, secret, issuer
        );

        Map<String, Object> response = new HashMap<>();
        response.put("secret", secret);
        response.put("otpAuthUrl", otpAuthUrl);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/enable-2fa")
    public ResponseEntity<?> enableTwoFactor(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String ipCliente = obtenerIPCliente();
        String hashedUsername = HashUtil.hashUsername(username);

        UsuarioDTO user = usuarioService.findByUsername(hashedUsername);
        if (user == null) {
            auditService.registrarEvento("ANONIMO", "ENABLE_2FA_FALLIDO", 
                "Usuario no encontrado: " + username, ipCliente, 
                "/api/auth/enable-2fa", "POST");
            
            return ResponseEntity.status(404)
                .body(createResponse("error", "Usuario no encontrado", null));
        }

        String secret = twoFactorService.generateSecret();
        String otpAuthUrl = twoFactorService.getOtpAuthURL(username, secret);

        usuarioService.updateSecret2FA(hashedUsername, secret);
        usuarioRepository.updateTwoFactorEnabled(hashedUsername, true);

        // ✅ Registrar habilitación de 2FA
        auditService.registrarEvento(username, "ENABLE_2FA", 
            "2FA habilitado", ipCliente, "/api/auth/enable-2fa", "POST");

        Map<String, String> data = new HashMap<>();
        data.put("secret", secret);
        data.put("otpAuthUrl", otpAuthUrl);
        return ResponseEntity.ok(createResponse("success", "2FA habilitado", data));
    }
}