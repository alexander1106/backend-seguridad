package Proyecto.MegaWeb2.__BackEnd.Service;

import Proyecto.MegaWeb2.__BackEnd.Dto.AdminDeleteDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.AdminRequestDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.AdminUpdateRequestDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.ListarAdminDTO;
import Proyecto.MegaWeb2.__BackEnd.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder; // Usa bean centralizado

    public int registrarAdministrador(AdminRequestDTO dto) {
        // Encriptar la contraseña antes de registrar
        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(hashedPassword);

        return adminRepository.crearAdministrador(dto);
    }

    public List<ListarAdminDTO> listarAdministradores() {
        return adminRepository.listarAdministradores();
    }

    public int editarAdministrador(AdminUpdateRequestDTO dto) {
        if (dto.getNuevaPassword() != null && !dto.getNuevaPassword().trim().isEmpty()) {
            if (dto.getPasswordActual() == null || dto.getConfirmarPassword() == null) {
                throw new RuntimeException("Debe proporcionar la contraseña actual y confirmarla.");
            }

            String actualEncriptada = jdbcTemplate.queryForObject(
                "SELECT password FROM users WHERE id = ?", new Object[]{dto.getId()}, String.class
            );

            if (!passwordEncoder.matches(dto.getPasswordActual(), actualEncriptada)) {
                throw new RuntimeException("La contraseña actual es incorrecta.");
            }

            if (!dto.getNuevaPassword().equals(dto.getConfirmarPassword())) {
                throw new RuntimeException("La nueva contraseña y la confirmación no coinciden.");
            }

            dto.setNuevaPassword(passwordEncoder.encode(dto.getNuevaPassword()));
        } else {
            dto.setNuevaPassword(null);
        }

        return adminRepository.editarAdministrador(dto);
    }
	public Integer eliminarUsuario(Integer id) {
		return adminRepository.eliminarUsuarioLogico(id);
	}

}
