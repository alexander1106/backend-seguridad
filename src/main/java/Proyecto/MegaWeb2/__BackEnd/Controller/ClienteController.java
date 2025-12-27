package Proyecto.MegaWeb2.__BackEnd.Controller;

import Proyecto.MegaWeb2.__BackEnd.Dto.ClienteListadoDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.VistaClienteDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.CrearClienteDTO;
import Proyecto.MegaWeb2.__BackEnd.Service.ClienteService;
import Proyecto.MegaWeb2.__BackEnd.Service.PermisoRolModuloService;
import Proyecto.MegaWeb2.__BackEnd.Security.UsuarioSesionUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Lista de clientes visibles en la web")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private PermisoRolModuloService permisoRolModuloService;

	private static final int ID_MODULO_CLIENTES = 4;

	@Operation(summary = "Listar clientes activos", description = "Devuelve una lista de clientes con nombre e imagen")
	@GetMapping
	public ResponseEntity<List<ClienteListadoDTO>> listarClientes() {
		return ResponseEntity.ok(clienteService.listarClientes());
	}

	@Operation(summary = "Vista de cliente: productos asignados", description = "Devuelve los productos asociados al cliente")
	@GetMapping("/vista/{idCliente}")
	public ResponseEntity<?> vistaCliente(@PathVariable int idCliente) {
		List<VistaClienteDTO> vista = clienteService.obtenerVistaCliente(idCliente);
		return ResponseEntity.ok(vista);
	}

	@Operation(summary = "Registrar un nuevo cliente", description = "Registra un nuevo cliente en la base de datos (requiere autenticación)", responses = {
			@ApiResponse(responseCode = "200", description = "Cliente creado correctamente"),
			@ApiResponse(responseCode = "403", description = "No tienes permisos"),
			@ApiResponse(responseCode = "500", description = "Error al registrar cliente") })
	@PostMapping
	@SecurityRequirement(name = "bearerAuth")
	public ResponseEntity<?> registrarCliente(@RequestParam String empresa, @RequestParam String descripcion,
			@RequestParam String ruc, @RequestParam(required = false) String grupo, @RequestParam String contacto,
			@RequestParam(required = false) String nombreComercial, @RequestParam String telefono, @RequestParam String localidad,
			@RequestParam(required = false) String direccion, @RequestParam(required = false) String imagen,
			@RequestParam Short showWeb, HttpServletRequest request) {
		Integer idRol = UsuarioSesionUtil.getIdRolActual();
		if (idRol == null) {
			return ResponseEntity.status(401).body("{\"error\": \"No autenticado\"}");
		}

		if (!permisoRolModuloService.tienePermiso(idRol, ID_MODULO_CLIENTES, "pCreate")) {
			return ResponseEntity.status(403).body("{\"error\": \"No tienes permiso para registrar clientes\"}");
		}

		CrearClienteDTO dto = new CrearClienteDTO();
		dto.setEmpresa(empresa);
		dto.setDescripcion(descripcion);
		dto.setNruc(ruc);
		dto.setGrupo(grupo);
		dto.setContacto(contacto);
		dto.setNombreComercial(nombreComercial);
		dto.setTelefono(telefono);
		dto.setDireccion(direccion);
		dto.setLocalidad(localidad);
		dto.setLogo(imagen);
		dto.setShowWeb(showWeb);

		try {
			boolean creado = clienteService.crearCliente(dto);
			if (creado) {
				return ResponseEntity.ok("{\"mensaje\": \"Cliente registrado correctamente\"}");
			} else {
				return ResponseEntity.status(500).body("{\"error\": \"No se pudo registrar el cliente\"}");
			}
		} catch (Exception e) {
			return ResponseEntity.status(400).body("{\"error\": \"" + e.getMessage() + "\"}");
		}
	}

}
