package Proyecto.MegaWeb2.__BackEnd.Service;

import Proyecto.MegaWeb2.__BackEnd.Dto.BusquedaRecienteDTO;
import Proyecto.MegaWeb2.__BackEnd.Repository.BusquedaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusquedaService {

    @Autowired
    private BusquedaRepository busquedaRepository;

    public List<BusquedaRecienteDTO> obtenerRecientes() {
        return busquedaRepository.obtenerBusquedasRecientes();
    }
}
