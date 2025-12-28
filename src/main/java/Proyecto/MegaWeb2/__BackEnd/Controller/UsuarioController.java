package Proyecto.MegaWeb2.__BackEnd.Controller;

import Proyecto.MegaWeb2.__BackEnd.Dto.UsuarioCreateRequestDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.EditarUsuarioDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.ListarUsuarioDTO;
import Proyecto.MegaWeb2.__BackEnd.Service.UsuarioService;
import Proyecto.MegaWeb2.__BackEnd.Service.AuditService;
import Proyecto.MegaWeb2.__BackEnd.Util.URLEncryptionUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuditService auditService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService, AuditService auditService) {
        this.usuarioService = usuarioService;
        this.auditService = auditService;
    }

    // --- Métodos de Ayuda ---
    private String obtenerIPCliente(HttpServletRequest request) {
        String xf = request.getHeader("X-Forwarded-For");
        return (xf != null && !xf.isEmpty()) ? xf.split(",")[0].trim() : request.getRemoteAddr();
    }

    // --- Endpoints ---

    @PostMapping
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody UsuarioCreateRequestDTO dto, HttpServletRequest request) {
        int idUsuario = usuarioService.crearUsuario(dto);
        if (idUsuario > 0) {
            String idEncriptado = URLEncryptionUtil.encriptarId(idUsuario);
            auditService.registrarEvento("SISTEMA", "CREAR_USUARIO", "Creado: " + dto.getEmail(), obtenerIPCliente(request), "/api/auth/usuarios", "POST");
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("idUsuario", idEncriptado));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "No se pudo crear el usuario"));
    }

    @GetMapping
    public ResponseEntity<List<ListarUsuarioDTO>> listarUsuarios(HttpServletRequest request) {
        auditService.registrarAccesoRecurso("USUARIO", "/api/auth/usuarios", obtenerIPCliente(request));
        return ResponseEntity.ok(usuarioService.listarUsuarios(null));
    }

    @GetMapping("/{idEncriptado}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable String idEncriptado, HttpServletRequest request) {
        Integer id = URLEncryptionUtil.desencriptarId(idEncriptado);
        if (id == null) return ResponseEntity.badRequest().body(Map.of("error", "ID inválido"));

        List<ListarUsuarioDTO> lista = usuarioService.listarUsuarios(id);
        if (lista.isEmpty()) return ResponseEntity.notFound().build();

        auditService.registrarAccesoRecurso("USUARIO", "/api/auth/usuarios/" + id, obtenerIPCliente(request));
        return ResponseEntity.ok(lista.get(0));
    }

    @PutMapping
    public ResponseEntity<?> editarUsuario(@Valid @RequestBody EditarUsuarioDTO dto, HttpServletRequest request) {
        try {
            usuarioService.editarUsuario(dto);
            String usuarioLog = (String) request.getAttribute("username");
            auditService.registrarEvento(usuarioLog, "ACTUALIZAR_USUARIO", "ID: " + dto.getId(), obtenerIPCliente(request), "/api/auth/usuarios", "PUT");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al actualizar"));
        }
    }

    @DeleteMapping("/{idEncriptado}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable String idEncriptado, HttpServletRequest request) {
        Integer id = URLEncryptionUtil.desencriptarId(idEncriptado);
        if (id == null) return ResponseEntity.badRequest().body(Map.of("error", "ID inválido"));

        try {
            usuarioService.eliminarUsuario(id);
            String usuarioLog = (String) request.getAttribute("username");
            auditService.registrarEliminacion(usuarioLog, id, "USUARIO", obtenerIPCliente(request));
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Error al eliminar"));
        }
    }
}