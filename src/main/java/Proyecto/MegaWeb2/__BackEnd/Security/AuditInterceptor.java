
package Proyecto.MegaWeb2.__BackEnd.Security;

import Proyecto.MegaWeb2.__BackEnd.Service.AuditService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuditInterceptor extends OncePerRequestFilter {

    @Autowired(required = false)
    private AuditService auditService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                   HttpServletResponse response, 
                                   FilterChain filterChain) throws ServletException, IOException {
        
        String endpoint = request.getRequestURI();
        String metodo = request.getMethod();
        String ipAddress = obtenerIPCliente(request);
        
        try {
            filterChain.doFilter(request, response);
        } finally {
            // Registrar después de procesar la request
            if (auditService != null && debeRegistrarse(endpoint, metodo)) {
                String usuario = (String) request.getAttribute("username");
                if (usuario == null) {
                    usuario = "ANONIMO";
                }
                
                // No loguear endpoints públicos innecesarios
                if (!endpoint.contains("/api/productos") && 
                    !endpoint.contains("/api/clientes") &&
                    !endpoint.contains("/swagger") &&
                    !endpoint.contains("/v3/api-docs")) {
                    
                    auditService.registrarEvento(usuario, "API_CALL", 
                        metodo + " " + endpoint, ipAddress, endpoint, metodo);
                }
            }
        }
    }

    /**
     * Determina si un endpoint debe ser registrado
     */
    private boolean debeRegistrarse(String endpoint, String metodo) {
        // Registrar todas las operaciones POST, PUT, DELETE
        return metodo.equals("POST") || metodo.equals("PUT") || metodo.equals("DELETE");
    }

    /**
     * Obtiene la IP del cliente considerando proxies
     */
    private String obtenerIPCliente(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        
        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }
        
        return request.getRemoteAddr();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // No filtrar archivos estáticos ni swagger
        return path.startsWith("/css") || path.startsWith("/js") || 
               path.startsWith("/images") || path.startsWith("/swagger-ui");
    }
}