package Proyecto.MegaWeb2.__BackEnd.Service;

import Proyecto.MegaWeb2.__BackEnd.Dto.ListarModuloDTO;
import Proyecto.MegaWeb2.__BackEnd.Repository.ModuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ModuloService {

    @Autowired
    private ModuloRepository moduloRepository;

    public List<ListarModuloDTO> listarModulos(Integer idModulo) {
        return moduloRepository.listarModulos(idModulo);
    }
}
