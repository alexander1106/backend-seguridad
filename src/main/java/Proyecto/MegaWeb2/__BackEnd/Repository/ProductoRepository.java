package Proyecto.MegaWeb2.__BackEnd.Repository;

import Proyecto.MegaWeb2.__BackEnd.Dto.CrearProductoDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.DocProductoDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.ActualizarProductoDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.ListarProductoDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.MultimediaProductoDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.VistaProductoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<ListarProductoDTO> listarProductos() {
        return jdbcTemplate.query("CALL sp_listar_productos()", new RowMapper<ListarProductoDTO>() {
            @Override
            public ListarProductoDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
                ListarProductoDTO dto = new ListarProductoDTO();
                dto.setId(rs.getInt("id"));
                dto.setImagen(rs.getString("imagen"));
                dto.setNombre(rs.getString("nombre"));
                dto.setDescripcion(rs.getString("descripcion"));
                return dto;
            }
        });
    }

    public int crearProducto(CrearProductoDTO dto) {
        return jdbcTemplate.query(
            "CALL sp_crear_producto(?, ?, ?, ?, ?, ?, ?, ?, ?)",
            new Object[]{
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getImagen(),
                dto.getCaracteristicas(),
                dto.getDescarga(),
                dto.getVideo(),
                dto.getCaja(),
                dto.getTitdescarga(),
                dto.getUltimaversion()
            },
            rs -> rs.next() ? rs.getInt("idProducto") : 0
        );
    }
    
    //Actualizar producto
    public int actualizarProducto(ActualizarProductoDTO dto) {
        return jdbcTemplate.update(
            "CALL sp_actualizar_producto(?, ?, ?, ?)",
            dto.getId(),
            dto.getNombre(),
            dto.getDescripcion(),
            dto.getImagen()
        );
    }
    
    //VistaProducto
    public VistaProductoDTO obtenerVistaProducto(int idProducto) {
        VistaProductoDTO dto = new VistaProductoDTO();

        jdbcTemplate.execute(con -> {
            CallableStatement cs = con.prepareCall("CALL sp_ver_detalle_producto(?)");
            cs.setInt(1, idProducto);
            return cs;
        }, (CallableStatementCallback<Object>) cs -> {
            boolean hasResults = cs.execute();

            // 1. Datos del producto
            if (hasResults) {
                try (ResultSet rs = cs.getResultSet()) {
                    if (rs.next()) {
                        dto.setId(rs.getInt("id"));
                        dto.setNombre(rs.getString("nombre"));
                        dto.setDescripcion(rs.getString("descripcion"));
                        dto.setImagen(rs.getString("imagen"));
                        dto.setCaracteristicas(rs.getString("caracteristicas"));
                        dto.setVideo(rs.getString("video"));
                    }
                }
            }

            // 2. Documentación
            if (cs.getMoreResults()) {
                try (ResultSet rs2 = cs.getResultSet()) {
                    List<DocProductoDTO> docs = new ArrayList<>();
                    while (rs2.next()) {
                        DocProductoDTO doc = new DocProductoDTO();
                        doc.setDocument(rs2.getString("document"));
                        doc.setDescripcion(rs2.getString("descripcion"));
                        docs.add(doc);
                    }
                    dto.setDocumentacion(docs);
                }
            }

            // 3. Multimedia
            if (cs.getMoreResults()) {
                try (ResultSet rs3 = cs.getResultSet()) {
                    List<MultimediaProductoDTO> imgs = new ArrayList<>();
                    while (rs3.next()) {
                        MultimediaProductoDTO img = new MultimediaProductoDTO();
                        img.setImagen(rs3.getString("imagen"));
                        img.setDescripcion(rs3.getString("descripcion"));
                        imgs.add(img);
                    }
                    dto.setMultimedia(imgs);
                }
            }

            return null;
        });

        return dto;
    }


}
