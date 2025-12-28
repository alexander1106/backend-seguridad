// AuditLog.java - Entidad JPA
package Proyecto.MegaWeb2.__BackEnd.Model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria_logs")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario", nullable = false)
    private String usuario;

    @Column(name = "accion", nullable = false)
    private String accion;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "endpoint")
    private String endpoint;

    @Column(name = "metodo_http")
    private String metodoHttp;

    @Column(name = "estado_respuesta")
    private Integer estadoRespuesta;

    // Constructor vacío
    public AuditLog() {
    }

    // Constructor con parámetros comunes
    public AuditLog(String usuario, String accion, String descripcion, 
                   LocalDateTime fecha, String ipAddress, String endpoint) {
        this.usuario = usuario;
        this.accion = accion;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.ipAddress = ipAddress;
        this.endpoint = endpoint;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getMetodoHttp() {
        return metodoHttp;
    }

    public void setMetodoHttp(String metodoHttp) {
        this.metodoHttp = metodoHttp;
    }

    public Integer getEstadoRespuesta() {
        return estadoRespuesta;
    }

    public void setEstadoRespuesta(Integer estadoRespuesta) {
        this.estadoRespuesta = estadoRespuesta;
    }

    @Override
    public String toString() {
        return "AuditLog{" +
                "id=" + id +
                ", usuario='" + usuario + '\'' +
                ", accion='" + accion + '\'' +
                ", fecha=" + fecha +
                ", ipAddress='" + ipAddress + '\'' +
                ", endpoint='" + endpoint + '\'' +
                '}';
    }
}
