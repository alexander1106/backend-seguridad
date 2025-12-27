package Proyecto.MegaWeb2.__BackEnd.Service;

import Proyecto.MegaWeb2.__BackEnd.Dto.LicenciaClienteDTO;
import Proyecto.MegaWeb2.__BackEnd.Repository.LicenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LicenciaService {

    @Autowired
    private LicenciaRepository licenciaRepository;

    public List<LicenciaClienteDTO> consultarLicencias(Integer idCliente) {
        return licenciaRepository.consultarLicencias(idCliente);
    }
}
