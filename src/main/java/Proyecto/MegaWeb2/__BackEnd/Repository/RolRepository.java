package Proyecto.MegaWeb2.__BackEnd.Repository;
import Proyecto.MegaWeb2.__BackEnd.Dto.RolDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RolRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Crear un rol usando SP y retornar el id generado
    public int crearRol(RolDTO dto) {
        return jdbcTemplate.query(
            "CALL sp_crear_rol(?, ?, ?, ?)",
            new Object[]{
                dto.getNombre(),
            },
            rs -> {
                if (rs.next()) {
                    return rs.getInt("idRol");
                }
                return 0;
            }
        );
    }
  // Acualizar Roles
    public int actualizarRol(RolDTO dto) {
        return jdbcTemplate.update(
            "CALL sp_actualizar_rol(?, ?)",
            dto.getIdRol(),
            dto.getNombre()
        );
    }
    
    // Listar Roles
 // Método para listar roles (todos o uno, según el parámetro)
    public List<RolDTO> listarRoles(Integer idRol) {
        return jdbcTemplate.query(
            "CALL sp_listar_roles(?)",
            new Object[]{idRol},
            (rs, rowNum) -> {
                RolDTO dto = new RolDTO();
                dto.setIdRol(rs.getInt("idRol"));
                dto.setNombre(rs.getString("nombre"));
                return dto;
            }
        );
    }

}
