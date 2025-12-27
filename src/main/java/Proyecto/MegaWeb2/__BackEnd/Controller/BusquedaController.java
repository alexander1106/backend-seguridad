package Proyecto.MegaWeb2.__BackEnd.Controller;

import Proyecto.MegaWeb2.__BackEnd.Dto.BusquedaRecienteDTO;
import Proyecto.MegaWeb2.__BackEnd.Service.BusquedaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/busquedas")
@Tag(name = "Búsquedas", description = "Últimas búsquedas recientes de los usuarios")
public class BusquedaController {

    @Autowired
    private BusquedaService busquedaService;

    @Operation(summary = "Obtener las 5 últimas búsquedas recientes")
    @GetMapping("/recientes")
    public ResponseEntity<List<BusquedaRecienteDTO>> obtenerRecientes() {
        return ResponseEntity.ok(busquedaService.obtenerRecientes());
    }
}
