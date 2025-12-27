package Proyecto.MegaWeb2.__BackEnd.Service;

import Proyecto.MegaWeb2.__BackEnd.Dto.ClienteListadoDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.CrearClienteDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.VistaClienteDTO;
import Proyecto.MegaWeb2.__BackEnd.Repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteListadoDTO> listarClientes() {
        return clienteRepository.listarClientes();
    }
    
    //Producto de cliente
    public List<VistaClienteDTO> obtenerVistaCliente(int idCliente) {
        return clienteRepository.obtenerVistaCliente(idCliente);
    }
    
    public boolean crearCliente(CrearClienteDTO dto) {
        try {
            int filasAfectadas = clienteRepository.insertarCliente(dto);
            return filasAfectadas > 0;
        } catch (Exception e) {
            throw new RuntimeException("Error al crear cliente: " + e.getMessage(), e);
        }
    }
}
