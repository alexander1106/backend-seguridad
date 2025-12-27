package Proyecto.MegaWeb2.__BackEnd.Service;

import Proyecto.MegaWeb2.__BackEnd.Dto.UltimaPublicacionDTO;
import Proyecto.MegaWeb2.__BackEnd.Repository.PublicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    public List<UltimaPublicacionDTO> obtenerUltimasPublicaciones() {
        return publicacionRepository.listarUltimasPublicaciones();
    }
}
