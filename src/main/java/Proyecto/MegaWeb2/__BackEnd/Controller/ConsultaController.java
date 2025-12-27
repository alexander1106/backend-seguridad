package Proyecto.MegaWeb2.__BackEnd.Controller;

import Proyecto.MegaWeb2.__BackEnd.Dto.ConsultaListadoDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.VistaConsultaDTO;
import Proyecto.MegaWeb2.__BackEnd.Service.ConsultaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consultas")
@Tag(name = "Consultas", description = "Vista de consultas con su producto relacionado")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @Operation(summary = "Listar todas las consultas con su producto")
    @GetMapping
    public ResponseEntity<List<ConsultaListadoDTO>> listarConsultas() {
        List<ConsultaListadoDTO> consultas = consultaService.listarConsultas();
        return ResponseEntity.ok(consultas);
    }

    @Operation(
        summary = "Obtener detalles de una consulta",
        description = "Devuelve título, producto, descripción, pasos e imágenes de una consulta",
        parameters = @Parameter(name = "idConsulta", description = "ID de la consulta", required = true),
        responses = {
            @ApiResponse(responseCode = "200", description = "Consulta obtenida"),
            @ApiResponse(responseCode = "404", description = "Consulta no encontrada")
        }
    )
    @GetMapping("/vista")
    public ResponseEntity<?> obtenerConsulta(@RequestParam int idConsulta) {
        VistaConsultaDTO consulta = consultaService.obtenerConsulta(idConsulta);
        if (consulta == null) {
            return ResponseEntity.status(404).body("{\"error\": \"Consulta no encontrada\"}");
        }
        return ResponseEntity.ok(consulta);
    }
}
