package Proyecto.MegaWeb2.__BackEnd.Controller;

import Proyecto.MegaWeb2.__BackEnd.Dto.NosotrosDTO;
import Proyecto.MegaWeb2.__BackEnd.Security.UsuarioSesionUtil;
import Proyecto.MegaWeb2.__BackEnd.Service.EmpresaService;
import Proyecto.MegaWeb2.__BackEnd.Service.PermisoRolModuloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/nosotros")
@Tag(name = "Nosotros", description = "Información de la empresa: descripción, misión y visión")
@SecurityRequirement(name = "bearerAuth")
public class EmpresaController {

	@Autowired
	private EmpresaService empresaService;

	@Autowired
	private PermisoRolModuloService permisoRolModuloService;

	@Operation(summary = "Obtener información institucional")
	@GetMapping
	public ResponseEntity<?> obtenerInformacion() {
		NosotrosDTO dto = empresaService.obtenerInformacionEmpresa();
		if (dto == null) {
			return ResponseEntity.status(404).body("{\"error\": \"No se encontró información de la empresa\"}");
		}
		return ResponseEntity.ok(dto);
	}

	@Operation(summary = "Actualizar información institucional (campos opcionales)")
	@PutMapping
	public ResponseEntity<?> actualizarInformacion(@RequestParam(required = false) String descripcion,
			@RequestParam(required = false) String mision, @RequestParam(required = false) String vision,
			@RequestParam(required = false) String telefono, @RequestParam(required = false) String direccion,
			HttpServletRequest request) {
		Integer idRol = UsuarioSesionUtil.getIdRolActual();
		if (idRol == null) {
			return ResponseEntity.status(401).body("{\"error\": \"No autenticado\"}");
		}

		final int ID_MODULO_NOSOTROS = 10;
		if (!permisoRolModuloService.tienePermiso(idRol, ID_MODULO_NOSOTROS, "pUpdate")) {
			return ResponseEntity.status(403)
					.body("{\"error\": \"No tienes permiso para actualizar la información de la empresa\"}");
		}

		// Obtener información actual
		NosotrosDTO actual = empresaService.obtenerInformacionEmpresa();
		if (actual == null) {
			return ResponseEntity.status(404).body("{\"error\": \"No se encontró información de la empresa\"}");
		}

		// Solo sobrescribir campos que llegan no nulos
		if (descripcion != null)
			actual.setDescripcion(descripcion);
		if (mision != null)
			actual.setMision(mision);
		if (vision != null)
			actual.setVision(vision);
		if (telefono != null)
			actual.setTelefono(telefono);
		if (direccion != null)
			actual.setDireccion(direccion);

		boolean actualizado = empresaService.actualizarInformacionEmpresa(actual);
		if (actualizado) {
			return ResponseEntity.ok("{\"status\": \"Información actualizada correctamente\"}");
		} else {
			return ResponseEntity.status(500).body("{\"error\": \"No se pudo actualizar la información\"}");
		}
	}

}
