package Proyecto.MegaWeb2.__BackEnd.Controller;

import Proyecto.MegaWeb2.__BackEnd.Dto.PermisoRolModuloDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.PermisoRolModuloAsignarDTO;
import Proyecto.MegaWeb2.__BackEnd.Service.PermisoRolModuloService;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/permisos-rol-modulo")
@Tag(name = "Permisos por Rol y Módulo", description = "Endpoints para consultar y gestionar los permisos de un rol sobre los módulos del sistema.")
public class PermisoRolModuloController {

    @Autowired
    private PermisoRolModuloService permisoRolModuloService;

    private final int ID_MODULO_ACCESOS = 7; // Reemplaza con el ID real del módulo "Accesos"

    @Operation(
        summary = "Obtener los permisos de un rol sobre todos los módulos",
        description = "Devuelve una lista de módulos y los permisos asociados (ver, crear, editar, eliminar) para el rol especificado.",
        parameters = {
            @Parameter(name = "idRol", description = "ID del rol a consultar", required = true)
        },
        responses = {
            @ApiResponse(responseCode = "200", description = "Permisos obtenidos correctamente"),
            @ApiResponse(responseCode = "403", description = "No autorizado")
        }
    )
    @GetMapping("/{idRol}")
    public ResponseEntity<?> listarPermisosPorRol(@PathVariable int idRol, HttpServletRequest request) {
        Integer rolActual = (Integer) request.getAttribute("idRol");
        if (rolActual == null || !permisoRolModuloService.tienePermiso(rolActual, ID_MODULO_ACCESOS, "pView")) {
            return ResponseEntity.status(403).body("{\"error\": \"No tienes permiso para ver accesos\"}");
        }

        List<PermisoRolModuloDTO> permisos = permisoRolModuloService.listarPermisosPorRol(idRol);
        return ResponseEntity.ok(permisos);
    }

    @Operation(
        summary = "Asignar o actualizar permisos de un rol sobre un módulo",
        description = "Permite asignar o actualizar los permisos (ver, crear, editar, eliminar) de un rol sobre un módulo específico.",
        parameters = {
            @Parameter(name = "idRol", description = "ID del rol", required = true),
            @Parameter(name = "idModulo", description = "ID del módulo", required = true),
            @Parameter(name = "pView", description = "Permiso ver (1 o 0)", required = true),
            @Parameter(name = "pCreate", description = "Permiso crear (1 o 0)", required = true),
            @Parameter(name = "pUpdate", description = "Permiso editar (1 o 0)", required = true),
            @Parameter(name = "pDelete", description = "Permiso eliminar (1 o 0)", required = true)
        }
    )
    @PutMapping("/asignar")
    public ResponseEntity<?> asignarPermisosRolModulo(
            @RequestParam int idRol,
            @RequestParam int idModulo,
            @RequestParam int pView,
            @RequestParam int pCreate,
            @RequestParam int pUpdate,
            @RequestParam int pDelete,
            HttpServletRequest request) {

        Integer rolActual = (Integer) request.getAttribute("idRol");
        if (rolActual == null || !permisoRolModuloService.tienePermiso(rolActual, ID_MODULO_ACCESOS, "pCreate")) {
            return ResponseEntity.status(403).body("{\"error\": \"No tienes permiso para asignar accesos\"}");
        }

        PermisoRolModuloAsignarDTO dto = new PermisoRolModuloAsignarDTO();
        dto.setIdRol(idRol);
        dto.setIdModulo(idModulo);
        dto.setpView(pView);
        dto.setpCreate(pCreate);
        dto.setpUpdate(pUpdate);
        dto.setpDelete(pDelete);

        int result = permisoRolModuloService.asignarPermisosRolModulo(dto);
        if (result > 0) {
            return ResponseEntity.ok().body("{\"status\": \"Permisos asignados o actualizados correctamente\"}");
        } else {
            return ResponseEntity.status(500).body("{\"error\": \"No se pudo asignar/actualizar permisos\"}");
        }
    }
}
