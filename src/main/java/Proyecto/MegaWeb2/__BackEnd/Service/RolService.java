package Proyecto.MegaWeb2.__BackEnd.Service;

import Proyecto.MegaWeb2.__BackEnd.Dto.RolDTO;
import Proyecto.MegaWeb2.__BackEnd.Repository.RolRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService {

	@Autowired
	private RolRepository rolRepository;

	// Retorna el id del rol creado
	public int crearRol(RolDTO dto) {
		return rolRepository.crearRol(dto);
	}

	//Actualizar
	public int actualizarRol(RolDTO dto) {
        return rolRepository.actualizarRol(dto);
    }
	
	// Listar todos los roles o uno por id
    public List<RolDTO> listarRoles(Integer idRol) {
        return rolRepository.listarRoles(idRol);
    }

}
