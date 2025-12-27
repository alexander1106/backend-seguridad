package Proyecto.MegaWeb2.__BackEnd.Repository;

import Proyecto.MegaWeb2.__BackEnd.Dto.ConsultaListadoDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.VistaConsultaDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.VistaConsultaDTO.PasoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class ConsultaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ConsultaListadoDTO> listarConsultas() {
        return jdbcTemplate.query("CALL sp_listar_consultas()", new RowMapper<ConsultaListadoDTO>() {
            @Override
            public ConsultaListadoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                ConsultaListadoDTO dto = new ConsultaListadoDTO();
                dto.setId(rs.getInt("id"));
                dto.setTitulo(rs.getString("titulo"));
                dto.setDescripcion(rs.getString("descripcion"));
                dto.setProducto(rs.getString("producto"));
                return dto;
            }
        });
    }
    
    // Vista de consulta
    public VistaConsultaDTO obtenerVistaConsulta(int idConsulta) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("CALL sp_vista_consulta(?)", idConsulta);

        if (rows.isEmpty()) return null;

        VistaConsultaDTO vista = new VistaConsultaDTO();
        List<PasoDTO> pasos = new ArrayList<>();

        for (Map<String, Object> row : rows) {
            if (vista.getTituloConsulta() == null) {
                vista.setTituloConsulta((String) row.get("tituloConsulta"));
                vista.setNombreProducto((String) row.get("nombreProducto"));
                vista.setDescripcionConsulta((String) row.get("descripcionConsulta"));
            }

            PasoDTO paso = new PasoDTO();
            paso.setTituloPaso((String) row.get("tituloPaso"));
            paso.setDescripcionPaso((String) row.get("descripcionPaso"));
            paso.setImagenPaso((String) row.get("imagenPaso"));
            pasos.add(paso);
        }

        vista.setPasos(pasos);
        return vista;
    }
}
