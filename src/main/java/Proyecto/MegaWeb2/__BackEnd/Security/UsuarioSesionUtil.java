package Proyecto.MegaWeb2.__BackEnd.Security;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UsuarioSesionUtil {

    // Para obtener el rol actual del usuario autenticado
    public static Integer getIdRolActual() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            Object idRol = attrs.getRequest().getAttribute("idRol");
            if (idRol instanceof Integer) return (Integer) idRol;
            if (idRol instanceof Long) return ((Long) idRol).intValue();
        }
        return null;
    }

    // Para obtener el idUsuario actual del token
    public static Integer getIdUsuarioActual() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            Object idUsuario = attrs.getRequest().getAttribute("idUsuario");
            if (idUsuario instanceof Integer) return (Integer) idUsuario;
            if (idUsuario instanceof Long) return ((Long) idUsuario).intValue();
        }
        return null;
    }
}
