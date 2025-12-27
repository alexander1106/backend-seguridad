package Proyecto.MegaWeb2.__BackEnd.Repository;

import Proyecto.MegaWeb2.__BackEnd.Dto.LicenciaClienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class LicenciaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<LicenciaClienteDTO> consultarLicencias(Integer idCliente) {
        return jdbcTemplate.query(
            "CALL sp_consultar_licencias_cliente(?)",
            new Object[]{idCliente},
            new RowMapper<LicenciaClienteDTO>() {
                @Override
                public LicenciaClienteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                    LicenciaClienteDTO dto = new LicenciaClienteDTO();
                    dto.setIdCliente(rs.getInt("idCliente"));
                    dto.setNombreCliente(rs.getString("nombreCliente"));
                    dto.setIdProducto(rs.getInt("idProducto"));
                    dto.setNombreProducto(rs.getString("nombreProducto"));
                    dto.setFechasetup(rs.getString("fechasetup"));
                    dto.setFechasupdate(rs.getString("fechasupdate"));
                    dto.setVersionupdate(rs.getString("versionupdate"));
                    dto.setLicseriehd(rs.getString("licseriehd"));
                    dto.setLiccpuid(rs.getString("liccpuid"));
                    dto.setLicnumusuario(rs.getInt("licnumusuario"));
                    dto.setLicnumserie(rs.getString("licnumserie"));
                    dto.setLicencia(rs.getInt("licencia"));
                    dto.setFecha_i(rs.getString("fecha_i"));
                    dto.setFecha_f(rs.getString("fecha_f"));
                    dto.setMonto(rs.getDouble("monto"));
                    dto.setEstadolicencia(rs.getInt("estadolicencia"));
                    return dto;
                }
            }
        );
    }
}
