package Proyecto.MegaWeb2.__BackEnd.Controller;

import Proyecto.MegaWeb2.__BackEnd.Dto.UsuarioCreateRequestDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.EditarUsuarioDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.ListarUsuarioDTO;
import Proyecto.MegaWeb2.__BackEnd.Service.UsuarioService;
import Proyecto.MegaWeb2.__BackEnd.Service.AuditService;
import Proyecto.MegaWeb2.__BackEnd.Util.URLEncryptionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuditService auditService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, AuditService auditService) {
        this.usuarioService = usuarioService;
        this.auditService = auditService;
    }

    /**
     * Helper: Obtiene la IP real del cliente (útil si estás detrás de Nginx/Cloudflare)
     */
    private String obtenerIPCliente(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    /**
     * Helper: Obtiene el usuario actual del request (inyectado por el filtro JWT)
     */
    private String obtenerUsuarioActual(HttpServletRequest request) {
        String usuario = (String) request.getAttribute("username");
        return (usuario != null) ? usuario : "ANONYMOUS";
    }

    // ==========================================
    // CREAR (POST)
    // ==========================================
    @PostMapping
    public ResponseEntity<Map<String, Object>> crearUsuario(
            @Valid @RequestBody UsuarioCreateRequestDTO dto,
            HttpServletRequest request
    ) {
        String ipCliente = obtenerIPCliente(request);
        
        try {
            int idUsuario = usuarioService.crearUsuario(dto);
            
            if (idUsuario > 0) {
                // Encriptamos el ID para devolverlo al frontend
                String idEncriptado = URLEncryptionUtil.encriptarId(idUsuario);

                auditService.registrarEvento("SISTEMA", "CREAR_USUARIO", 
                    "Usuario creado: " + dto.getEmail(), ipCliente, "/api/auth/usuarios", "POST");
                
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(Map.of("idUsuario", idEncriptado, "status", "success"));
            } else {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "No se pudo crear el usuario"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // ==========================================
    // LISTAR (GET)
    // ==========================================
    @GetMapping
    public ResponseEntity<List<ListarUsuarioDTO>> listarUsuarios(HttpServletRequest request) {
        String ipCliente = obtenerIPCliente(request);
        // Auditoría ligera de acceso (opcional, puede llenar mucho la base de datos)
        auditService.registrarAccesoRecurso("ADMIN", "/api/auth/usuarios", ipCliente);
        
        List<ListarUsuarioDTO> lista = usuarioService.listarUsuarios(null);
        return ResponseEntity.ok(lista);
    }

    // ==========================================
    // OBTENER POR ID (GET /{hash})
    // ==========================================
    @GetMapping("/{idEncriptado}")
    public ResponseEntity<?> obtenerUsuario(
            @PathVariable String idEncriptado,
            HttpServletRequest request
    ) {
        String ipCliente = obtenerIPCliente(request);

        try {
            Integer id = URLEncryptionUtil.desencriptarId(idEncriptado);
            if (id == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "ID inválido"));
            }

            List<ListarUsuarioDTO> lista = usuarioService.listarUsuarios(id);
            if (lista.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            auditService.registrarAccesoRecurso("ADMIN", "/api/auth/usuarios/" + id, ipCliente);
            return ResponseEntity.ok(lista.get(0));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al procesar ID"));
        }
    }

    // ==========================================
    // EDITAR (PUT)
    // ==========================================
    @PutMapping
    public ResponseEntity<?> editarUsuario(
            @RequestBody EditarUsuarioDTO dto,
            HttpServletRequest request
    ) {
        String ipCliente = obtenerIPCliente(request);
        String usuarioActual = obtenerUsuarioActual(request);

        try {
            // NOTA: Se asume que EditarUsuarioDTO maneja la conversión interna 
            // o que el servicio descifra el ID si viene encriptado dentro del DTO.
            usuarioService.editarUsuario(dto);

            auditService.registrarEvento(usuarioActual, "ACTUALIZAR_USUARIO", 
                "Usuario actualizado (DTO ID: " + dto.getId() + ")", ipCliente, 
                "/api/auth/usuarios", "PUT");

            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al actualizar usuario: " + e.getMessage()));
        }
    }

    // ==========================================
    // ELIMINAR (DELETE /{hash})
    // ==========================================
    @DeleteMapping("/{idEncriptado}")
    public ResponseEntity<?> eliminarUsuario(
            @PathVariable String idEncriptado,
            HttpServletRequest request
    ) {
        String ipCliente = obtenerIPCliente(request);
        String usuarioActual = obtenerUsuarioActual(request);

        try {
            // 1. Desencriptar
            Integer id = URLEncryptionUtil.desencriptarId(idEncriptado);
            if (id == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "ID inválido o manipulado"));
            }

            // 2. Ejecutar lógica de negocio
            usuarioService.eliminarUsuario(id);

            // 3. Auditar
            auditService.registrarEliminacion(usuarioActual, id, "USUARIO", ipCliente);

            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Error al eliminar usuario"));
        }
    }
}
