package Proyecto.MegaWeb2.__BackEnd.Service;

import Proyecto.MegaWeb2.__BackEnd.Dto.ConsultaListadoDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.VistaConsultaDTO;
import Proyecto.MegaWeb2.__BackEnd.Repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    public List<ConsultaListadoDTO> listarConsultas() {
        return consultaRepository.listarConsultas();
    }
    
    // Visa de consulta
    public VistaConsultaDTO obtenerConsulta(int idConsulta) {
        return consultaRepository.obtenerVistaConsulta(idConsulta);
    }
}
