package Proyecto.MegaWeb2.__BackEnd.Service;

import Proyecto.MegaWeb2.__BackEnd.Dto.PermisoRolModuloDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.PermisoRolModuloAsignarDTO;
import Proyecto.MegaWeb2.__BackEnd.Repository.PermisoRolModuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PermisoRolModuloService {

    @Autowired
    private PermisoRolModuloRepository permisoRolModuloRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<PermisoRolModuloDTO> listarPermisosPorRol(int idRol) {
        return permisoRolModuloRepository.listarPermisosPorRol(idRol);
    }
    
    public int asignarPermisosRolModulo(PermisoRolModuloAsignarDTO dto) {
        return permisoRolModuloRepository.asignarPermisosRolModulo(dto);
    }

    // --- Nuevo método para validar permisos ---
    public boolean tienePermiso(int idRol, int idModulo, String permiso) {
        // Valida el nombre del campo para evitar inyección SQL
        if (!permiso.equals("pView") && !permiso.equals("pCreate") && !permiso.equals("pUpdate") && !permiso.equals("pDelete")) {
            throw new IllegalArgumentException("Permiso inválido");
        }
        String sql = "SELECT " + permiso + " FROM detallerollmodulo WHERE idRol = ? AND idModulo = ?";
        try {
            Integer resultado = jdbcTemplate.queryForObject(sql, Integer.class, idRol, idModulo);
            return resultado != null && resultado == 1;
        } catch (Exception e) {
            return false;
        }
    }
}