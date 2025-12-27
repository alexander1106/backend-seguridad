package Proyecto.MegaWeb2.__BackEnd.Repository;

import Proyecto.MegaWeb2.__BackEnd.Dto.ListarModuloDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ModuloRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ListarModuloDTO> listarModulos(Integer idModulo) {
        return jdbcTemplate.query(
            "CALL sp_listar_modulos(?)",
            new Object[]{idModulo},
            (rs, rowNum) -> {
                ListarModuloDTO dto = new ListarModuloDTO();
                dto.setIdModulo(rs.getInt("idModulo"));
                dto.setNombre(rs.getString("nombre"));
                dto.setDescripcion(rs.getString("descripcion"));
                dto.setCreatedAt(rs.getString("created_at"));
                dto.setUpdatedAt(rs.getString("updated_at"));
                return dto;
            }
        );
    }
}
