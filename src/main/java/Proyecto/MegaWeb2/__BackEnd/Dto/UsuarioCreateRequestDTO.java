package Proyecto.MegaWeb2.__BackEnd.Dto;

public class UsuarioCreateRequestDTO {
	private String nombres;
	private String apellidos;
	private String username;
	private String password;
	private String email;
	private Integer suscripcion;
	private int idRol;

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

	public Integer getSuscripcion() {
		return suscripcion;
	}

	public void setSuscripcion(Integer suscripcion) {
		this.suscripcion = suscripcion;
	}

	

	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	@Override
	public String toString() {
		return "UsuarioCreateRequestDTO [nombres=" + nombres + ", apellidos=" + apellidos + ", username=" + username
				+ ", password=" + password + ", email=" + email + ", suscripcion=" + suscripcion +  ", idRol=" + idRol + "]";
	}

}
