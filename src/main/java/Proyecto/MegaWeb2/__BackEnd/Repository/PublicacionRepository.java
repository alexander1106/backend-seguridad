package Proyecto.MegaWeb2.__BackEnd.Repository;

import Proyecto.MegaWeb2.__BackEnd.Dto.UltimaPublicacionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class PublicacionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<UltimaPublicacionDTO> listarUltimasPublicaciones() {
        return jdbcTemplate.query("CALL sp_listar_ultimas_publicaciones()", new RowMapper<UltimaPublicacionDTO>() {
            @Override
            public UltimaPublicacionDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                UltimaPublicacionDTO dto = new UltimaPublicacionDTO();
                dto.setId(rs.getInt("id"));
                dto.setTitulo(rs.getString("titulo"));
                dto.setDescripcion(rs.getString("descripcion"));
                dto.setFecha(rs.getTimestamp("fecha").toLocalDateTime());
                dto.setImagen(rs.getString("imagen"));
                return dto;
            }
        });
    }
}
