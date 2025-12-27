package Proyecto.MegaWeb2.__BackEnd.Dto;

public class EditarUsuarioDTO {

	private Integer id;
	private String nombres;
	private String apellidos;
	private String email;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "EditarUsuarioDTO [id=" + id + ", nombres=" + nombres + ", apellidos=" + apellidos + ", email=" + email
				+ "]";
	}

}
