package Proyecto.MegaWeb2.__BackEnd.Repository;

import Proyecto.MegaWeb2.__BackEnd.Dto.ClienteListadoDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.CrearClienteDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.VistaClienteDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ClienteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ClienteListadoDTO> listarClientes() {
        return jdbcTemplate.query("CALL sp_listar_clientes()", new RowMapper<ClienteListadoDTO>() {
            @Override
            public ClienteListadoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                ClienteListadoDTO dto = new ClienteListadoDTO();
                dto.setId(rs.getInt("id"));
                dto.setNombreCliente(rs.getString("nombre_cliente"));
                dto.setImagenCliente(rs.getString("imagen_cliente"));
                return dto;
            }
        });
    }
    
    //Productos de clientes
    public List<VistaClienteDTO> obtenerVistaCliente(int idCliente) {
        return jdbcTemplate.query("CALL sp_vista_cliente(?)", new Object[]{idCliente}, new RowMapper<VistaClienteDTO>() {
            @Override
            public VistaClienteDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                VistaClienteDTO dto = new VistaClienteDTO();
                dto.setIdcliente(rs.getInt("idcliente"));
                dto.setNombreCliente(rs.getString("nombre_cliente"));
                dto.setNombreProducto(rs.getString("nombre_producto"));
                return dto;
            }
        });
    }
    
    //Registrar Clientes
    public int insertarCliente(CrearClienteDTO dto) {
        return jdbcTemplate.update(
            "CALL sp_insertar_cliente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            dto.getEmpresa(),
            dto.getDescripcion(),
            dto.getLogo(),
            dto.getNruc(),
            dto.getTelefono(),
            dto.getContacto(),
            dto.getNombreComercial(),
            dto.getDireccion(),
            dto.getLocalidad(),
            dto.getGrupo(),
            dto.getShowWeb()
        );
    }
}
