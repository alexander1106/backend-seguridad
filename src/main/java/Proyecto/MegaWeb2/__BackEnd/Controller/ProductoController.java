package Proyecto.MegaWeb2.__BackEnd.Controller;

import Proyecto.MegaWeb2.__BackEnd.Dto.ActualizarProductoDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.CrearProductoDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.ListarProductoDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.VistaProductoDTO;
import Proyecto.MegaWeb2.__BackEnd.Security.UsuarioSesionUtil;
import Proyecto.MegaWeb2.__BackEnd.Service.PermisoRolModuloService;
import Proyecto.MegaWeb2.__BackEnd.Service.ProductoService;

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

    private static final int ID_MODULO_PRODUCTOS = 5;

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
    
    @GetMapping("/{id}")
    public ResponseEntity<?> verDetalleProducto(@PathVariable int id) {
        VistaProductoDTO dto = productoService.obtenerVistaProducto(id);
        if (dto == null || dto.getNombre() == null) {
            return ResponseEntity.status(404).body("{\"error\": \"Producto no encontrado\"}");
        }
        return ResponseEntity.ok(dto);
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
    public ResponseEntity<?> crear(@RequestBody CrearProductoDTO dto, HttpServletRequest request) {
        Integer idRol = UsuarioSesionUtil.getIdRolActual();
        if (idRol == null) {
            return ResponseEntity.status(401).body("{\"error\": \"No autenticado\"}");
        }

        if (!permisoRolModuloService.tienePermiso(idRol, ID_MODULO_PRODUCTOS, "pCreate")) {
            return ResponseEntity.status(403).body("{\"error\": \"No tienes permiso para crear productos\"}");
        }

        int idProducto = productoService.crearProducto(dto);
        if (idProducto > 0) {
            return ResponseEntity.ok().body("{\"idProducto\": " + idProducto + "}");
        } else {
            return ResponseEntity.status(500).body("{\"error\": \"No se pudo crear el producto\"}");
        }
    }
    
    @PutMapping
    @io.swagger.v3.oas.annotations.security.SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> actualizar(@RequestBody ActualizarProductoDTO dto, HttpServletRequest request) {
        Integer idRol = UsuarioSesionUtil.getIdRolActual();
        if (idRol == null) {
            return ResponseEntity.status(401).body("{\"error\": \"No autenticado\"}");
        }

        final int ID_MODULO_PRODUCTS = 5; 
        if (!permisoRolModuloService.tienePermiso(idRol, ID_MODULO_PRODUCTS, "pUpdate")) {
            return ResponseEntity.status(403).body("{\"error\": \"No tienes permiso para actualizar productos\"}");
        }

        boolean actualizado = productoService.actualizarProducto(dto);
        if (actualizado) {
            return ResponseEntity.ok("{\"status\": \"Producto actualizado correctamente\"}");
        } else {
            return ResponseEntity.status(500).body("{\"error\": \"No se pudo actualizar el producto\"}");
        }
    }

}
