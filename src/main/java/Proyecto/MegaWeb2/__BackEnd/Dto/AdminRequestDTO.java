package Proyecto.MegaWeb2.__BackEnd.Dto;

public class AdminRequestDTO {
	private String nombres;
	private String apellidos;
	private String username;
	private String password;
	private String email;
	private Integer rol;

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getRol() {
		return rol;
	}

	public void setRol(Integer rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "AdminRequestDTO [nombres=" + nombres + ", apellidos=" + apellidos + ", username=" + username
				+ ", password=" + password + ", email=" + email + ", rol=" + rol + "]";
	}

}
