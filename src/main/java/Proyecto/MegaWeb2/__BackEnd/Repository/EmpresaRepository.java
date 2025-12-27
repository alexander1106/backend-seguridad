package Proyecto.MegaWeb2.__BackEnd.Repository;

import Proyecto.MegaWeb2.__BackEnd.Dto.EmpresaCreateRequestDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.NosotrosDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmpresaRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public NosotrosDTO obtenerInformacionEmpresa() {
        return jdbcTemplate.query("CALL sp_obtener_nosotros()", rs -> {
            if (rs.next()) {
                NosotrosDTO dto = new NosotrosDTO();
                dto.setDescripcion(rs.getString("descripcion"));
                dto.setMision(rs.getString("mision"));
                dto.setVision(rs.getString("vision"));
                dto.setTelefono(rs.getString("telefono"));
                dto.setDireccion(rs.getString("direccion"));
                return dto;
            }
            return null;
        });
    }

    
    //Actualizar Nosoros
    public int actualizarInformacionEmpresa(NosotrosDTO dto) {
        return jdbcTemplate.update(
            "CALL sp_actualizar_nosotros(?, ?, ?, ?, ?)",
            dto.getDescripcion(),
            dto.getMision(),
            dto.getVision(),
            dto.getTelefono(),
            dto.getDireccion()
        );
    }
    public int insertarEmpresa(EmpresaCreateRequestDTO dto) {
        return jdbcTemplate.update(
            "CALL sp_insertar_empresa(?, ?, ?, ?, ?)",
            dto.getPresentacion(),
            dto.getMision(),
            dto.getVision(),
            dto.getTelefono(),
            dto.getDireccion()
        );
    }


}
