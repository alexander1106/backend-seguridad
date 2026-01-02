package Proyecto.MegaWeb2.__BackEnd.Controller;

import Proyecto.MegaWeb2.__BackEnd.Dto.ActualizarProductoDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.CrearProductoDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.ListarProductoDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.VistaProductoDTO;
import Proyecto.MegaWeb2.__BackEnd.Security.UsuarioSesionUtil;
import Proyecto.MegaWeb2.__BackEnd.Service.PermisoRolModuloService;
import Proyecto.MegaWeb2.__BackEnd.Service.ProductoService;
import Proyecto.MegaWeb2.__BackEnd.Service.AuditService;
import Proyecto.MegaWeb2.__BackEnd.Util.URLEncryptionUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "API para gestionar productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PermisoRolModuloService permisoRolModuloService;

    @Autowired
    private AuditService auditService;

    private static final int ID_MODULO_PRODUCTOS = 5;

    private String obtenerIPCliente(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    @Operation(
        summary = "Listar productos disponibles",
        description = "Devuelve una lista con imagen, nombre y descripción de los productos.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Lista obtenida con éxito")
        }
    )
    @GetMapping
    public ResponseEntity<List<ListarProductoDTO>> listar() {
        return ResponseEntity.ok(productoService.listarProductos());
    }
    
    /**
     * CAMBIO: Ahora recibe ID encriptado en la URL
     * Antes: /api/productos/15
     * Ahora: /api/productos/Xy9-zRq2
     */
    @GetMapping("/{idEncriptado}")
    public ResponseEntity<?> verDetalleProducto(@PathVariable String idEncriptado) {
        try {
            // Desencriptar el ID
            Integer idReal = URLEncryptionUtil.desencriptarId(idEncriptado);
            
            if (idReal == null) {
                return ResponseEntity.status(400)
                    .body("{\"error\": \"ID inválido\"}");
            }

            VistaProductoDTO dto = productoService.obtenerVistaProducto(idReal);
            if (dto == null || dto.getNombre() == null) {
                return ResponseEntity.status(404)
                    .body("{\"error\": \"Producto no encontrado\"}");
            }
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(400)
                .body("{\"error\": \"Error al procesar la solicitud\"}");
        }
    }
    
    @Operation(
        summary = "Crear un nuevo producto",
        description = "Registra un nuevo producto en la base de datos.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Producto creado correctamente"),
            @ApiResponse(responseCode = "403", description = "No tienes permisos"),
            @ApiResponse(responseCode = "500", description = "Error al crear el producto")
        }
    )
    @PostMapping
    @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> crear(@RequestBody CrearProductoDTO dto, 
                                   HttpServletRequest request) {
        Integer idRol = UsuarioSesionUtil.getIdRolActual();
        String ipCliente = obtenerIPCliente(request);
        
        if (idRol == null) {
            return ResponseEntity.status(401)
                .body("{\"error\": \"No autenticado\"}");
        }

        if (!permisoRolModuloService.tienePermiso(idRol, ID_MODULO_PRODUCTOS, "pCreate")) {
            auditService.registrarDenegacionPermiso("ADMIN", "/api/productos", ipCliente);
            return ResponseEntity.status(403)
                .body("{\"error\": \"No tienes permiso para crear productos\"}");
        }

        int idProducto = productoService.crearProducto(dto);
        if (idProducto > 0) {
            // ✅ Encriptar el ID antes de devolverlo
            String idEncriptado = URLEncryptionUtil.encriptarId(idProducto);
            auditService.registrarEvento("ADMIN", "CREAR_PRODUCTO", 
                "Producto creado: " + dto.getNombre(), ipCliente, "/api/productos", "POST");
            
            return ResponseEntity.ok()
                .body("{\"idProducto\": \"" + idEncriptado + "\"}");
        } else {
            return ResponseEntity.status(500)
                .body("{\"error\": \"No se pudo crear el producto\"}");
        }
    }
    
    @PutMapping
    @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> actualizar(@RequestBody ActualizarProductoDTO dto, 
                                       HttpServletRequest request) {
        Integer idRol = UsuarioSesionUtil.getIdRolActual();
        String ipCliente = obtenerIPCliente(request);
        
        if (idRol == null) {
            return ResponseEntity.status(401)
                .body("{\"error\": \"No autenticado\"}");
        }

        final int ID_MODULO_PRODUCTS = 5; 
        if (!permisoRolModuloService.tienePermiso(idRol, ID_MODULO_PRODUCTS, "pUpdate")) {
            auditService.registrarDenegacionPermiso("ADMIN", "/api/productos", ipCliente);
            return ResponseEntity.status(403)
                .body("{\"error\": \"No tienes permiso para actualizar productos\"}");
        }

        boolean actualizado = productoService.actualizarProducto(dto);
        if (actualizado) {
            auditService.registrarEvento("ADMIN", "ACTUALIZAR_PRODUCTO", 
                "Producto actualizado ID: " + dto.getId(), ipCliente, 
                "/api/productos", "PUT");
            
            return ResponseEntity.ok("{\"status\": \"Producto actualizado correctamente\"}");
        } else {
            return ResponseEntity.status(500)
                .body("{\"error\": \"No se pudo actualizar el producto\"}");
        }
    }
}
