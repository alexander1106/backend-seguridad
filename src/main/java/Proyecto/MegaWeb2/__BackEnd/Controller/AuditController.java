// AuditController.java - Controlador para ver logs
package Proyecto.MegaWeb2.__BackEnd.Controller;

import Proyecto.MegaWeb2.__BackEnd.Model.AuditLog;
import Proyecto.MegaWeb2.__BackEnd.Service.AuditService;
import Proyecto.MegaWeb2.__BackEnd.Service.PermisoRolModuloService;
import Proyecto.MegaWeb2.__BackEnd.Security.UsuarioSesionUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/auditoria")
@Tag(name = "Auditoría", description = "Gestión de logs y auditoría del sistema")
public class AuditController {

    @Autowired
    private AuditService auditService;

    @Autowired
    private PermisoRolModuloService permisoRolModuloService;

    private static final int ID_MODULO_AUDITORIA = 12; // Ajusta al ID real

    @Operation(summary = "Obtener todos los logs de auditoría (Solo Admin)")
    @GetMapping
    public ResponseEntity<?> obtenerLogs() {
        Integer idRol = UsuarioSesionUtil.getIdRolActual();
        
        if (idRol == null) {
            return ResponseEntity.status(401).body("{\"error\": \"No autenticado\"}");
        }

        if (!permisoRolModuloService.tienePermiso(idRol, ID_MODULO_AUDITORIA, "pView")) {
            return ResponseEntity.status(403).body("{\"error\": \"No tienes permiso para ver auditoría\"}");
        }

        List<AuditLog> logs = auditService.obtenerTodosLogs();
        return ResponseEntity.ok(logs);
    }

    @Operation(summary = "Obtener logs de un usuario específico")
    @GetMapping("/usuario/{usuario}")
    public ResponseEntity<?> obtenerLogsPorUsuario(@PathVariable String usuario) {
        Integer idRol = UsuarioSesionUtil.getIdRolActual();
        
        if (idRol == null) {
            return ResponseEntity.status(401).body("{\"error\": \"No autenticado\"}");
        }

        if (!permisoRolModuloService.tienePermiso(idRol, ID_MODULO_AUDITORIA, "pView")) {
            return ResponseEntity.status(403).body("{\"error\": \"No tienes permiso\"}");
        }

        List<AuditLog> logs = auditService.obtenerLogsPorUsuario(usuario);
        return ResponseEntity.ok(logs);
    }

    @Operation(summary = "Obtener logs por tipo de acción")
    @GetMapping("/accion/{accion}")
    public ResponseEntity<?> obtenerLogsPorAccion(@PathVariable String accion) {
        Integer idRol = UsuarioSesionUtil.getIdRolActual();
        
        if (idRol == null) {
            return ResponseEntity.status(401).body("{\"error\": \"No autenticado\"}");
        }

        if (!permisoRolModuloService.tienePermiso(idRol, ID_MODULO_AUDITORIA, "pView")) {
            return ResponseEntity.status(403).body("{\"error\": \"No tienes permiso\"}");
        }

        List<AuditLog> logs = auditService.obtenerLogsPorAccion(accion);
        return ResponseEntity.ok(logs);
    }
}