// UsuarioService.java
package Proyecto.MegaWeb2.__BackEnd.Service;
import Proyecto.MegaWeb2.__BackEnd.Model.User; // 👈 agrega esta línea

import Proyecto.MegaWeb2.__BackEnd.Dto.UsuarioCreateRequestDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.EditarUsuarioDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.ListarUsuarioDTO;
import Proyecto.MegaWeb2.__BackEnd.Dto.UsuarioDTO;
import Proyecto.MegaWeb2.__BackEnd.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	/**
	 * Crear un nuevo usuario con hash de contraseña
	 */
public int crearUsuario(UsuarioCreateRequestDTO dto) {
    // Hashear password con BCrypt
    String hashedPassword = passwordEncoder.encode(dto.getPassword());
    dto.setPassword(hashedPassword);

    // Hashear username
    String hashedUsername = HashUtil.hashUsername(dto.getUsername());
    dto.setUsername(hashedUsername);

    return usuarioRepository.crearUsuario(dto);
}
	/**
	 * Listar usuarios (todos o por id)
	 */
	public List<ListarUsuarioDTO> listarUsuarios(Integer id) {
		return usuarioRepository.listarUsuarios(id);
	}

	/**
	 * Editar usuario
	 */
	public void editarUsuario(EditarUsuarioDTO dto) {
		usuarioRepository.editarUsuario(dto);
	}

	/**
	 * Eliminar usuario
	 */
	public void eliminarUsuario(Integer id) {
		usuarioRepository.eliminarUsuario(id);
	}

	/**
	 * Autenticar usuario por username y password (para login)
	 */
public UsuarioDTO authenticate(String username, String password) {
    // Hash determinista del username ingresado
    String hashedUsername = HashUtil.hashUsername(username);

    UsuarioDTO user = usuarioRepository.findByUsername(hashedUsername);

    if (user == null) return null;

    // Validar password con BCrypt
    if (!passwordEncoder.matches(password, user.getPasswordHash())) {
        return null;
    }
    return user;
}

public void updatePassword(String email, String nuevaPassword) {
    String hashedPassword = new BCryptPasswordEncoder().encode(nuevaPassword);
    usuarioRepository.actualizarPasswordPorEmail(email, hashedPassword);
}

	/**
	 * Recuperar UsuarioDTO por username (para generación de JWT tras OTP)
	 */
	public UsuarioDTO findByUsername(String username) {
		return usuarioRepository.findByUsername(username);
	}

	public void updateExpirationToken(String username, Date expirationToken) {
		usuarioRepository.actualizarExpirationToken(username, expirationToken);
	}

	public void removeExpirationToken(String username) {
		usuarioRepository.removeExpirationToken(username);
	}

	public void updateExpirationTokenWithUserId(Integer userId, Date expirationToken) {
		usuarioRepository.actualizarExpirationTokenWithUserId(userId, expirationToken);
	}
public UsuarioDTO findByEmail(String email) {
    return usuarioRepository.findByEmail(email);
}
public void updateSecret2FA(String username, String secret) {
    usuarioRepository.updateSecret2FA(username, secret);
}
public void updateTwoFactorEnabled(String username, boolean enabled) {
    usuarioRepository.updateTwoFactorEnabled(username, enabled);
}
}