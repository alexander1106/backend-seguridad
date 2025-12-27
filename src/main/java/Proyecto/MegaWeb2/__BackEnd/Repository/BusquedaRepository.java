package Proyecto.MegaWeb2.__BackEnd.Repository;

import Proyecto.MegaWeb2.__BackEnd.Dto.BusquedaRecienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BusquedaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<BusquedaRecienteDTO> obtenerBusquedasRecientes() {
        return jdbcTemplate.query("CALL sp_busquedas_recientes()", new RowMapper<BusquedaRecienteDTO>() {
            @Override
            public BusquedaRecienteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                BusquedaRecienteDTO dto = new BusquedaRecienteDTO();
                dto.setTitulo(rs.getString("titulo"));
                return dto;
            }
        });
    }
}
