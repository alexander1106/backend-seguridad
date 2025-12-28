package Proyecto.MegaWeb2.__BackEnd.Controller;

import Proyecto.MegaWeb2.__BackEnd.Dto.ClienteListadoDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.VistaClienteDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.CrearClienteDTO;
import Proyecto.MegaWeb2.__BackEnd.Service.ClienteService;
import Proyecto.MegaWeb2.__BackEnd.Service.PermisoRolModuloService;
import Proyecto.MegaWeb2.__BackEnd.Service.AuditService;
import Proyecto.MegaWeb2.__BackEnd.Security.UsuarioSesionUtil;
import Proyecto.MegaWeb2.__BackEnd.Util.URLEncryptionUtil;
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

    @Autowired
    private AuditService auditService;

    private static final int ID_MODULO_CLIENTES = 4;

    private String obtenerIPCliente(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    @Operation(summary = "Listar clientes activos", 
               description = "Devuelve una lista de clientes con nombre e imagen")
    @GetMapping
    public ResponseEntity<List<ClienteListadoDTO>> listarClientes() {
        return ResponseEntity.ok(clienteService.listarClientes());
    }

    
    @Operation(summary = "Vista de cliente: productos asignados", 
               description = "Devuelve los productos asociados al cliente")
    @GetMapping("/vista/{idClienteEncriptado}")
    public ResponseEntity<?> vistaCliente(@PathVariable String idClienteEncriptado) {
        try {
            Integer idCliente = URLEncryptionUtil.desencriptarId(idClienteEncriptado);
            
            if (idCliente == null) {
                return ResponseEntity.status(400)
                    .body("{\"error\": \"ID inválido\"}");
            }

            List<VistaClienteDTO> vista = clienteService.obtenerVistaCliente(idCliente);
            return ResponseEntity.ok(vista);
        } catch (Exception e) {
            return ResponseEntity.status(400)
                .body("{\"error\": \"Error al procesar la solicitud\"}");
        }
    }

    @Operation(summary = "Registrar un nuevo cliente", 
               description = "Registra un nuevo cliente en la base de datos")
    @PostMapping
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> registrarCliente(@RequestParam String empresa, 
                                             @RequestParam String descripcion,
                                             @RequestParam String ruc, 
                                             @RequestParam(required = false) String grupo, 
                                             @RequestParam String contacto,
                                             @RequestParam(required = false) String nombreComercial, 
                                             @RequestParam String telefono, 
                                             @RequestParam String localidad,
                                             @RequestParam(required = false) String direccion, 
                                             @RequestParam(required = false) String imagen,
                                             @RequestParam Short showWeb, 
                                             HttpServletRequest request) {
        Integer idRol = UsuarioSesionUtil.getIdRolActual();
        String ipCliente = obtenerIPCliente(request);
        
        if (idRol == null) {
            return ResponseEntity.status(401).body("{\"error\": \"No autenticado\"}");
        }

        if (!permisoRolModuloService.tienePermiso(idRol, ID_MODULO_CLIENTES, "pCreate")) {
            auditService.registrarDenegacionPermiso("ADMIN", "/api/clientes", ipCliente);
            return ResponseEntity.status(403)
                .body("{\"error\": \"No tienes permiso para registrar clientes\"}");
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
                auditService.registrarEvento("ADMIN", "CREAR_CLIENTE", 
                    "Cliente creado: " + empresa, ipCliente, "/api/clientes", "POST");
                
                return ResponseEntity.ok("{\"mensaje\": \"Cliente registrado correctamente\"}");
            } else {
                return ResponseEntity.status(500)
                    .body("{\"error\": \"No se pudo registrar el cliente\"}");
            }
        } catch (Exception e) {
            return ResponseEntity.status(400)
                .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}