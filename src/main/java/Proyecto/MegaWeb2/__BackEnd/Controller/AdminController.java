package Proyecto.MegaWeb2.__BackEnd.Controller;

import Proyecto.MegaWeb2.__BackEnd.Dto.AdminRequestDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.AdminUpdateRequestDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.ListarAdminDTO;
import Proyecto.MegaWeb2.__BackEnd.Service.AdminService;
import Proyecto.MegaWeb2.__BackEnd.Service.AuditService;
import Proyecto.MegaWeb2.__BackEnd.Service.PermisoRolModuloService;
import Proyecto.MegaWeb2.__BackEnd.Security.UsuarioSesionUtil;
import Proyecto.MegaWeb2.__BackEnd.Util.URLEncryptionUtil;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/administradores")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PermisoRolModuloService permisoRolModuloService;

    @Autowired
    private AuditService auditService;

    private static final int ID_MODULO_ADMINISTRADORES = 1;

    /**
     * Obtiene la IP del cliente
     */
    private String obtenerIPCliente(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    @PostMapping
    public ResponseEntity<?> crearAdministrador(
            @RequestParam String nombres, 
            @RequestParam String apellidos,
            @RequestParam String username, 
            @RequestParam String password, 
            @RequestParam String email,
            @RequestParam Integer rolNuevoAdministrador,
            HttpServletRequest request) {
        
        Integer idRolSesion = UsuarioSesionUtil.getIdRolActual();
        String ipCliente = obtenerIPCliente(request);
        
        if (idRolSesion == null) {
            return ResponseEntity.status(401).body("{\"error\": \"No autenticado\"}");
        }

        if (!permisoRolModuloService.tienePermiso(idRolSesion, ID_MODULO_ADMINISTRADORES, "pCreate")) {
            auditService.registrarDenegacionPermiso("ADMIN", "/api/administradores", ipCliente);
            return ResponseEntity.status(403).body("{\"error\": \"No tienes permisos para crear administradores\"}");
        }

        AdminRequestDTO dto = new AdminRequestDTO();
        dto.setNombres(nombres);
        dto.setApellidos(apellidos);
        dto.setUsername(username);
        dto.setPassword(password);
        dto.setEmail(email);
        dto.setRol(rolNuevoAdministrador);

        int idGenerado = adminService.registrarAdministrador(dto);
        if (idGenerado > 0) {
            // ✅ ENCRIPTAR EL ID ANTES DE DEVOLVERLO
            String idEncriptado = URLEncryptionUtil.encriptarId(idGenerado);
            auditService.registrarEvento("ADMIN", "CREAR_ADMINISTRADOR", 
                "Admin creado: " + username, ipCliente, "/api/administradores", "POST");
            
            return ResponseEntity.ok().body("{\"idAdministrador\": \"" + idEncriptado + "\"}");
        } else {
            return ResponseEntity.status(500).body("{\"error\": \"No se pudo crear el administrador\"}");
        }
    }

    @GetMapping
    public ResponseEntity<?> listarAdministradores(HttpServletRequest request) {
        
        Integer idRolSesion = UsuarioSesionUtil.getIdRolActual();
        String ipCliente = obtenerIPCliente(request);
        
        if (idRolSesion == null) {
            return ResponseEntity.status(401).body("{\"error\": \"No autenticado\"}");
        }

        if (!permisoRolModuloService.tienePermiso(idRolSesion, ID_MODULO_ADMINISTRADORES, "pView")) {
            auditService.registrarDenegacionPermiso("ADMIN", "/api/administradores", ipCliente);
            return ResponseEntity.status(403).body("{\"error\": \"No tienes permisos para ver administradores\"}");
        }

        List<ListarAdminDTO> lista = adminService.listarAdministradores();
        auditService.registrarAccesoRecurso("ADMIN", "/api/administradores", ipCliente);
        return ResponseEntity.ok(lista);
    }

    /**
     * CAMBIO: Recibe ID encriptado en la URL
     * Antes: /api/administradores?id=5
     * Ahora: PUT /api/administradores con ID encriptado en body
     */
    @PutMapping
    public ResponseEntity<?> actualizarAdministrador(
            @RequestParam String idEncriptado,
            @RequestParam String nombres,
            @RequestParam String apellidos, 
            @RequestParam String username, 
            @RequestParam String email,
            @RequestParam Integer rol, 
            @RequestParam(required = false) String passwordActual,
            @RequestParam(required = false) String nuevaPassword,
            @RequestParam(required = false) String confirmarPassword,
            HttpServletRequest request) {
        
        Integer idRolSesion = UsuarioSesionUtil.getIdRolActual();
        String ipCliente = obtenerIPCliente(request);
        
        if (idRolSesion == null) {
            return ResponseEntity.status(401).body("{\"error\": \"No autenticado\"}");
        }

        if (!permisoRolModuloService.tienePermiso(idRolSesion, ID_MODULO_ADMINISTRADORES, "pUpdate")) {
            auditService.registrarDenegacionPermiso("ADMIN", "/api/administradores", ipCliente);
            return ResponseEntity.status(403).body("{\"error\": \"No tienes permisos para editar administradores\"}");
        }
        
        try {
            // ✅ DESENCRIPTAR EL ID
            Integer id = URLEncryptionUtil.desencriptarId(idEncriptado);
            if (id == null) {
                return ResponseEntity.status(400).body("{\"error\": \"ID inválido\"}");
            }

            AdminUpdateRequestDTO dto = new AdminUpdateRequestDTO();
            dto.setId(id);
            dto.setNombres(nombres);
            dto.setApellidos(apellidos);
            dto.setUsername(username);
            dto.setEmail(email);
            dto.setRol(rol);
            dto.setPasswordActual(passwordActual);
            dto.setNuevaPassword(nuevaPassword);
            dto.setConfirmarPassword(confirmarPassword);

            adminService.editarAdministrador(dto);
            auditService.registrarEvento("ADMIN", "ACTUALIZAR_ADMINISTRADOR", 
                "Admin actualizado ID: " + id, ipCliente, "/api/administradores", "PUT");
            
            return ResponseEntity.ok("{\"mensaje\": \"Administrador actualizado correctamente\"}");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    /**
     * CAMBIO: Recibe ID encriptado
     * Antes: /api/administradores/eliminar?id=5
     * Ahora: /api/administradores/eliminar/Xy9-zRq2
     */
    @PostMapping("/eliminar/{idEncriptado}")
    public ResponseEntity<?> eliminarAdministrador(@PathVariable String idEncriptado, HttpServletRequest request) {
        
        Integer idRolSesion = UsuarioSesionUtil.getIdRolActual();
        String ipCliente = obtenerIPCliente(request);
        String usuarioAdmin = (String) request.getAttribute("username");
        
        if (idRolSesion == null) {
            return ResponseEntity.status(401).body("{\"error\": \"No autenticado\"}");
        }

        if (!permisoRolModuloService.tienePermiso(idRolSesion, ID_MODULO_ADMINISTRADORES, "pDelete")) {
            auditService.registrarDenegacionPermiso(usuarioAdmin, "/api/administradores/eliminar", ipCliente);
            return ResponseEntity.status(403).body("{\"error\": \"No tienes permisos para eliminar administradores\"}");
        }

        try {
            // ✅ DESENCRIPTAR EL ID
            Integer id = URLEncryptionUtil.desencriptarId(idEncriptado);
            if (id == null) {
                return ResponseEntity.status(400).body("{\"error\": \"ID inválido\"}");
            }

            Integer idEliminado = adminService.eliminarUsuario(id);
            auditService.registrarEliminacion(usuarioAdmin, id, "ADMINISTRADOR", ipCliente);
            
            // ✅ ENCRIPTAR EL ID EN LA RESPUESTA
            String idEliminadoEncriptado = URLEncryptionUtil.encriptarId(idEliminado);
            
            return ResponseEntity.ok("{\"mensaje\": \"Administrador eliminado correctamente\", \"idAdministrador\": \"" + idEliminadoEncriptado + "\"}");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}