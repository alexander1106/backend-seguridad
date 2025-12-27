package Proyecto.MegaWeb2.__BackEnd.Controller;

import Proyecto.MegaWeb2.__BackEnd.Dto.ListarModuloDTO;

import Proyecto.MegaWeb2.__BackEnd.Service.ModuloService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/modulos")
public class ModuloController {

    @Autowired
    private ModuloService moduloService;

    // Listar todos los módulos
    @GetMapping
    public ResponseEntity<List<ListarModuloDTO>> listarTodos() {
        List<ListarModuloDTO> modulos = moduloService.listarModulos(null);
        return ResponseEntity.ok(modulos);
    }

    // Listar módulo por id
    @GetMapping("/{id}")
    public ResponseEntity<ListarModuloDTO> listarPorId(@PathVariable int id) {
        List<ListarModuloDTO> modulos = moduloService.listarModulos(id);
        if (modulos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(modulos.get(0));
    }
}
