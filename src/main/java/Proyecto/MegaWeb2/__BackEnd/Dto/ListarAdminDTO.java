package Proyecto.MegaWeb2.__BackEnd.Dto;

public class ListarAdminDTO {
	private int id;
	private String nombres;
	private String apellidos;
	private String username;
	private String email;
	private String rol;

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

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "ListarAdminDTO [id=" + id + ", nombres=" + nombres + ", apellidos=" + apellidos + ", username="
				+ username + ", email=" + email + ", rol=" + rol + "]";
	}

}
