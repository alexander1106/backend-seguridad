package Proyecto.MegaWeb2.__BackEnd.Repository;

import Proyecto.MegaWeb2.__BackEnd.Model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    
    List<AuditLog> findByUsuarioOrderByFechaDesc(String usuario);
    
    List<AuditLog> findByAccionOrderByFechaDesc(String accion);
    
    // Encontrar logs en rango de fechas
    @Query("SELECT a FROM AuditLog a WHERE a.fecha BETWEEN :fechaInicio AND :fechaFin ORDER BY a.fecha DESC")
    List<AuditLog> findByFechaRange(@Param("fechaInicio") LocalDateTime fechaInicio, 
                                    @Param("fechaFin") LocalDateTime fechaFin);
    
    // Encontrar logs por IP
    List<AuditLog> findByIpAddressOrderByFechaDesc(String ipAddress);
    
    // Todos los logs ordenados por fecha descendente
    List<AuditLog> findAllByOrderByFechaDesc();
}