package Proyecto.MegaWeb2.__BackEnd.Controller;

import Proyecto.MegaWeb2.__BackEnd.Dto.UltimaPublicacionDTO;
import Proyecto.MegaWeb2.__BackEnd.Service.PublicacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publicaciones")
@Tag(name = "Publicaciones", description = "Muestra la lista de las últimas publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @Operation(summary = "Obtener últimas publicaciones")
    @GetMapping
    public ResponseEntity<List<UltimaPublicacionDTO>> listarUltimas() {
        return ResponseEntity.ok(publicacionService.obtenerUltimasPublicaciones());
    }
}
