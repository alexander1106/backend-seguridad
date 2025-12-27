package Proyecto.MegaWeb2.__BackEnd.Service;

import Proyecto.MegaWeb2.__BackEnd.Dto.EmpresaCreateRequestDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.NosotrosDTO;
import Proyecto.MegaWeb2.__BackEnd.Repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;
    
    //Lisar
    public NosotrosDTO obtenerInformacionEmpresa() {
        return empresaRepository.obtenerInformacionEmpresa();
    }
    
    //Actualizar
    public boolean actualizarInformacionEmpresa(NosotrosDTO dto) {
        return empresaRepository.actualizarInformacionEmpresa(dto) > 0;
    }
    
    public boolean crearEmpresa(EmpresaCreateRequestDTO dto) {
        return empresaRepository.insertarEmpresa(dto) > 0;
    }

}
