package Proyecto.MegaWeb2.__BackEnd.Controller;

import Proyecto.MegaWeb2.__BackEnd.Dto.RolDTO;
import Proyecto.MegaWeb2.__BackEnd.Service.RolService;
import Proyecto.MegaWeb2.__BackEnd.Service.PermisoRolModuloService;
import Proyecto.MegaWeb2.__BackEnd.Security.UsuarioSesionUtil;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @Autowired
    private PermisoRolModuloService permisoRolModuloService;

    private static final int ID_MODULO_ROLES = 9;

    // Crear Roles
    @PostMapping
    public ResponseEntity<?> crearRol(@RequestBody RolDTO dto) {
        Integer idRolActual = UsuarioSesionUtil.getIdRolActual();
        if (idRolActual == null) {
            return ResponseEntity.status(401).body("{\"error\": \"No autenticado\"}");
        }
        if (!permisoRolModuloService.tienePermiso(idRolActual, ID_MODULO_ROLES, "pCreate")) {
            return ResponseEntity.status(403).body("{\"error\": \"No tienes permisos para crear roles\"}");
        }

        int idRol = rolService.crearRol(dto);
        return ResponseEntity.ok().body("{\"idRol\": " + idRol + "}");
    }

    // Listar todos o por ID
    @GetMapping
    public ResponseEntity<?> listar(@RequestParam(required = false) Integer id) {
        Integer idRolActual = UsuarioSesionUtil.getIdRolActual();
        if (idRolActual == null) {
            return ResponseEntity.status(401).body("{\"error\": \"No autenticado\"}");
        }
        if (!permisoRolModuloService.tienePermiso(idRolActual, ID_MODULO_ROLES, "pView")) {
            return ResponseEntity.status(403).body("{\"error\": \"No tienes permisos para ver roles\"}");
        }

        List<RolDTO> roles = rolService.listarRoles(id);
        if (id != null) {
            if (roles.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(roles.get(0));
        } else {
            return ResponseEntity.ok(roles);
        }
    }
    
    //Actualizar rol
    @PutMapping("/{idRol}")
    public ResponseEntity<?> actualizarRol(
            @PathVariable int idRol,
            @RequestParam String nombre) {

        Integer idRolActual = UsuarioSesionUtil.getIdRolActual();
        if (idRolActual == null) {
            return ResponseEntity.status(401).body("{\"error\": \"No autenticado\"}");
        }

        final int ID_MODULO_ROLES = 9;
        if (!permisoRolModuloService.tienePermiso(idRolActual, ID_MODULO_ROLES, "pUpdate")) {
            return ResponseEntity.status(403).body("{\"error\": \"No tienes permisos para actualizar roles\"}");
        }

        RolDTO dto = new RolDTO();
        dto.setIdRol(idRol);
        dto.setNombre(nombre);

        int actualizado = rolService.actualizarRol(dto);
        if (actualizado > 0) {
            return ResponseEntity.ok().body("{\"status\": \"Rol actualizado correctamente\"}");
        } else {
            return ResponseEntity.status(404).body("{\"error\": \"Rol no encontrado\"}");
        }
    }


    
}
