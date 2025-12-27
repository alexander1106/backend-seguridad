package Proyecto.MegaWeb2.__BackEnd.Service;

import Proyecto.MegaWeb2.__BackEnd.Dto.CrearProductoDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.ActualizarProductoDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.ListarProductoDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.VistaProductoDTO;
import Proyecto.MegaWeb2.__BackEnd.Repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<ListarProductoDTO> listarProductos() {
        return productoRepository.listarProductos();
    }

    public int crearProducto(CrearProductoDTO dto) {
        return productoRepository.crearProducto(dto);
    }
    public boolean actualizarProducto(ActualizarProductoDTO dto) {
        return productoRepository.actualizarProducto(dto) > 0;
    }
    
    public VistaProductoDTO obtenerVistaProducto(int idProducto) {
        return productoRepository.obtenerVistaProducto(idProducto);
    }


}
