package Proyecto.MegaWeb2.__BackEnd.Repository;

import Proyecto.MegaWeb2.__BackEnd.Dto.AdminRequestDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.AdminUpdateRequestDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.ListarAdminDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class AdminRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Método para crear un nuevo administrador
    public int crearAdministrador(AdminRequestDTO dto) {
        return jdbcTemplate.query("CALL sp_crear_administrador(?, ?, ?, ?, ?, ?)",
                new Object[]{
                        dto.getNombres(),
                        dto.getApellidos(),
                        dto.getUsername(),
                        dto.getPassword(),
                        dto.getEmail(),
                        dto.getRol()
                },
                rs -> {
                    if (rs.next()) {
                        return rs.getInt("idUsuario");
                    }
                    return 0;
                });
    }
    
    public List<ListarAdminDTO> listarAdministradores() {
        return jdbcTemplate.query("CALL sp_listar_administradores()", (rs, rowNum) -> {
            ListarAdminDTO dto = new ListarAdminDTO();
            dto.setId(rs.getInt("id"));
            dto.setNombres(rs.getString("nombres"));
            dto.setApellidos(rs.getString("apellidos"));
            dto.setUsername(rs.getString("username"));
            dto.setEmail(rs.getString("email"));
            dto.setRol(rs.getString("rol"));
            return dto;
        });
    }
    public int editarAdministrador(AdminUpdateRequestDTO dto) {
        jdbcTemplate.update("CALL sp_editar_administrador(?, ?, ?, ?, ?, ?, ?)",
                dto.getId(),
                dto.getNombres(),
                dto.getApellidos(),
                dto.getUsername(),
                dto.getEmail(),
                dto.getRol(),
                dto.getNuevaPassword()
        );
        return 1; 
    }
    
    //Eliminar Administrador
    public Integer eliminarUsuarioLogico(Integer idUsuario) {
        String sql = "CALL sp_eliminar_Administrador(?)";
        return jdbcTemplate.queryForObject(sql, new Object[]{idUsuario}, Integer.class);
    }
}
