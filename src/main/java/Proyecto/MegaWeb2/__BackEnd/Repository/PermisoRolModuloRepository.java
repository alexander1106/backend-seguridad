package Proyecto.MegaWeb2.__BackEnd.Repository;

import Proyecto.MegaWeb2.__BackEnd.Dto.PermisoRolModuloDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.PermisoRolModuloAsignarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class PermisoRolModuloRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Listar todos los permisos de un rol sobre los módulos
    public List<PermisoRolModuloDTO> listarPermisosPorRol(int idRol) {
        return jdbcTemplate.query(
            "CALL sp_listar_permisos_rol_modulo(?)",
            new Object[]{idRol},
            (rs, rowNum) -> {
                PermisoRolModuloDTO dto = new PermisoRolModuloDTO();
                dto.setIdRol(rs.getInt("idRol"));
                dto.setIdModulo(rs.getInt("idModulo"));
                dto.setModulo(rs.getString("modulo"));
                dto.setpView(rs.getInt("pView"));
                dto.setpCreate(rs.getInt("pCreate"));
                dto.setpUpdate(rs.getInt("pUpdate"));
                dto.setpDelete(rs.getInt("pDelete"));
                dto.setCreatedAt(rs.getString("created_at"));
                dto.setUpdatedAt(rs.getString("updated_at"));
                return dto;
            }
        );
    }
    
    //Permiso Rol x Modulo
    public int asignarPermisosRolModulo(PermisoRolModuloAsignarDTO dto) {
        return jdbcTemplate.update(
            "CALL sp_asignar_permisos_rol_modulo(?, ?, ?, ?, ?, ?)",
            dto.getIdRol(),
            dto.getIdModulo(),
            dto.getpView(),
            dto.getpCreate(),
            dto.getpUpdate(),
            dto.getpDelete()
        );
    }
    
}
