package Proyecto.MegaWeb2.__BackEnd.Dto;

public class AdminUpdateRequestDTO {
	private int id;
	private String nombres;
	private String apellidos;
	private String username;
	private String email;
	private Integer rol;

	private String passwordActual;
	private String nuevaPassword;
	private String confirmarPassword;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getPasswordActual() {
		return passwordActual;
	}

	public void setPasswordActual(String passwordActual) {
		this.passwordActual = passwordActual;
	}

	public String getNuevaPassword() {
		return nuevaPassword;
	}

	public void setNuevaPassword(String nuevaPassword) {
		this.nuevaPassword = nuevaPassword;
	}

	public String getConfirmarPassword() {
		return confirmarPassword;
	}

	public void setConfirmarPassword(String confirmarPassword) {
		this.confirmarPassword = confirmarPassword;
	}

	@Override
	public String toString() {
		return "AdminUpdateRequestDTO [id=" + id + ", nombres=" + nombres + ", apellidos=" + apellidos + ", username="
				+ username + ", email=" + email + ", rol=" + rol + ", passwordActual=" + passwordActual
				+ ", nuevaPassword=" + nuevaPassword + ", confirmarPassword=" + confirmarPassword + "]";
	}

}
