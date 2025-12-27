package Proyecto.MegaWeb2.__BackEnd.Controller;

import Proyecto.MegaWeb2.__BackEnd.Dto.AdminRequestDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.AdminUpdateRequestDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.ListarAdminDTO;
import Proyecto.MegaWeb2.__BackEnd.Service.AdminService;
import Proyecto.MegaWeb2.__BackEnd.Service.PermisoRolModuloService;
import Proyecto.MegaWeb2.__BackEnd.Security.UsuarioSesionUtil;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

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

    private static final int ID_MODULO_ADMINISTRADORES = 1;

    @PostMapping
    public ResponseEntity<?> crearAdministrador(@RequestParam String nombres, @RequestParam String apellidos,
            @RequestParam String username, @RequestParam String password, @RequestParam String email,
            @RequestParam Integer rolNuevoAdministrador) {
        
        Integer idRolSesion = UsuarioSesionUtil.getIdRolActual();
        if (idRolSesion == null) {
            return ResponseEntity.status(401).body("{\"error\": \"No autenticado\"}");
        }

        if (!permisoRolModuloService.tienePermiso(idRolSesion, ID_MODULO_ADMINISTRADORES, "pCreate")) {
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
            return ResponseEntity.ok().body("{\"idAdministrador\": " + idGenerado + "}");
        } else {
            return ResponseEntity.status(500).body("{\"error\": \"No se pudo crear el administrador\"}");
        }
    }

    @GetMapping
    public ResponseEntity<?> listarAdministradores() {
        
        Integer idRolSesion = UsuarioSesionUtil.getIdRolActual();
        if (idRolSesion == null) {
            return ResponseEntity.status(401).body("{\"error\": \"No autenticado\"}");
        }

        if (!permisoRolModuloService.tienePermiso(idRolSesion, ID_MODULO_ADMINISTRADORES, "pView")) {
            return ResponseEntity.status(403).body("{\"error\": \"No tienes permisos para ver administradores\"}");
        }
        

        List<ListarAdminDTO> lista = adminService.listarAdministradores();
        return ResponseEntity.ok(lista);
    }

    @PutMapping
    public ResponseEntity<?> actualizarAdministrador(@RequestParam int id, @RequestParam String nombres,
            @RequestParam String apellidos, @RequestParam String username, @RequestParam String email,
            @RequestParam Integer rol, @RequestParam(required = false) String passwordActual,
            @RequestParam(required = false) String nuevaPassword,
            @RequestParam(required = false) String confirmarPassword) {
        
        Integer idRolSesion = UsuarioSesionUtil.getIdRolActual();
        if (idRolSesion == null) {
            return ResponseEntity.status(401).body("{\"error\": \"No autenticado\"}");
        }

        if (!permisoRolModuloService.tienePermiso(idRolSesion, ID_MODULO_ADMINISTRADORES, "pUpdate")) {
            return ResponseEntity.status(403).body("{\"error\": \"No tienes permisos para editar administradores\"}");
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

        try {
            adminService.editarAdministrador(dto);
            return ResponseEntity.ok("{\"mensaje\": \"Administrador actualizado correctamente\"}");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PostMapping("/eliminar")
    public ResponseEntity<?> eliminarAdministrador(@RequestParam Integer id) {
        
        Integer idRolSesion = UsuarioSesionUtil.getIdRolActual();
        if (idRolSesion == null) {
            return ResponseEntity.status(401).body("{\"error\": \"No autenticado\"}");
        }

        if (!permisoRolModuloService.tienePermiso(idRolSesion, ID_MODULO_ADMINISTRADORES, "pDelete")) {
            return ResponseEntity.status(403).body("{\"error\": \"No tienes permisos para eliminar administradores\"}");
        }
        

        try {
            Integer idEliminado = adminService.eliminarUsuario(id);
            return ResponseEntity.ok("{\"mensaje\": \"Administrador eliminado correctamente\", \"idAdministrador\": " + idEliminado + "}");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
