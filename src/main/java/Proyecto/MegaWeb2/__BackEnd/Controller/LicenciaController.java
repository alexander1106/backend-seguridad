package Proyecto.MegaWeb2.__BackEnd.Controller;

import Proyecto.MegaWeb2.__BackEnd.Dto.LicenciaClienteDTO;
import Proyecto.MegaWeb2.__BackEnd.Service.LicenciaService;
import Proyecto.MegaWeb2.__BackEnd.Service.PermisoRolModuloService;
import Proyecto.MegaWeb2.__BackEnd.Security.UsuarioSesionUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/licencias")
@Tag(name = "Licencias de Clientes", description = "Consulta de licencias activas por cliente")
public class LicenciaController {

	@Autowired
	private LicenciaService licenciaService;

	@Autowired
	private PermisoRolModuloService permisoRolModuloService;

	private static final int ID_MODULO_SOPORTE = 8; // Ajusta si tu módulo Soporte tiene otro ID

	@Operation(
		summary = "Consultar licencias por cliente",
		description = "Permite consultar todas las licencias activas o filtrar por ID de cliente.",
		parameters = {
			@Parameter(name = "idCliente", description = "ID del cliente a consultar (opcional)", required = false)
		},
		responses = {
			@ApiResponse(responseCode = "200", description = "Consulta exitosa"),
			@ApiResponse(responseCode = "403", description = "No tienes permiso para ver licencias"),
			@ApiResponse(responseCode = "500", description = "Error interno del servidor")
		}
	)
	@GetMapping
	public ResponseEntity<?> consultarLicencias(@RequestParam(required = false) Integer idCliente) {
		Integer idRol = UsuarioSesionUtil.getIdRolActual();

		if (idRol == null) {
			return ResponseEntity.status(401).body("{\"error\": \"No autenticado\"}");
		}

		if (!permisoRolModuloService.tienePermiso(idRol, ID_MODULO_SOPORTE, "pView")) {
			return ResponseEntity.status(403).body("{\"error\": \"No tienes permiso para consultar licencias\"}");
		}

		List<LicenciaClienteDTO> licencias = licenciaService.consultarLicencias(idCliente);

		if (idCliente != null && licencias.isEmpty()) {
			return ResponseEntity.status(404).body("{\"error\": \"Cliente no encontrado o sin licencias\"}");
		}

		return ResponseEntity.ok(licencias);
	}
}
