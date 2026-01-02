// AuditService.java - Servicio para registrar auditoría
package Proyecto.MegaWeb2.__BackEnd.Service;

import Proyecto.MegaWeb2.__BackEnd.Model.AuditLog;
import Proyecto.MegaWeb2.__BackEnd.Repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuditService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    /**
     * Registra un evento de auditoría
     */
    public void registrarEvento(String usuario, String accion, String descripcion, 
                               String ipAddress, String endpoint, String metodoHttp) {
        AuditLog log = new AuditLog();
        log.setUsuario(usuario != null ? usuario : "ANONIMO");
        log.setAccion(accion);
        log.setDescripcion(descripcion);
        log.setFecha(LocalDateTime.now());
        log.setIpAddress(ipAddress);
        log.setEndpoint(endpoint);
        log.setMetodoHttp(metodoHttp);
        
        auditLogRepository.save(log);
    }

    /**
     * Registra login exitoso
     */
    public void registrarLoginExitoso(String usuario, String ipAddress) {
        registrarEvento(usuario, "LOGIN", "Inicio de sesión exitoso", 
                       ipAddress, "/api/auth/login", "POST");
    }

    /**
     * Registra intento de login fallido
     */
    public void registrarLoginFallido(String usuario, String ipAddress, String razon) {
        registrarEvento(usuario, "LOGIN_FALLIDO", "Intento de login fallido: " + razon, 
                       ipAddress, "/api/auth/login", "POST");
    }

    /**
     * Registra eliminación de usuario
     */
    public void registrarEliminacion(String usuarioAdmin, Integer idEliminado, 
                                    String tipoEntidad, String ipAddress) {
        String descripcion = "Eliminado " + tipoEntidad + " ID: " + idEliminado;
        registrarEvento(usuarioAdmin, "ELIMINAR", descripcion, 
                       ipAddress, "/api/" + tipoEntidad.toLowerCase(), "DELETE");
    }

    /**
     * Registra cambio de contraseña
     */
    public void registrarCambioPassword(String usuario, String ipAddress) {
        registrarEvento(usuario, "CAMBIO_PASSWORD", "Cambio de contraseña", 
                       ipAddress, "/api/auth/cambiar-password", "PUT");
    }

    /**
     * Registra acceso a recurso protegido
     */
    public void registrarAccesoRecurso(String usuario, String recurso, String ipAddress) {
        registrarEvento(usuario, "ACCESO_RECURSO", "Acceso a: " + recurso, 
                       ipAddress, recurso, "GET");
    }

    /**
     * Registra error de permisos
     */
    public void registrarDenegacionPermiso(String usuario, String recurso, String ipAddress) {
        registrarEvento(usuario, "PERMISO_DENEGADO", "Intento acceso denegado a: " + recurso, 
                       ipAddress, recurso, "GET");
    }

    /**
     * Obtiene todos los logs
     */
    public List<AuditLog> obtenerTodosLogs() {
        return auditLogRepository.findAllByOrderByFechaDesc();
    }

    /**
     * Obtiene logs de un usuario específico
     */
    public List<AuditLog> obtenerLogsPorUsuario(String usuario) {
        return auditLogRepository.findByUsuarioOrderByFechaDesc(usuario);
    }

    /**
     * Obtiene logs por tipo de acción
     */
    public List<AuditLog> obtenerLogsPorAccion(String accion) {
        return auditLogRepository.findByAccionOrderByFechaDesc(accion);
    }
}

// ============================================================================

