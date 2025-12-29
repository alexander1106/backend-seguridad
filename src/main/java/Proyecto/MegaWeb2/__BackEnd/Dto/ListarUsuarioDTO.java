package Proyecto.MegaWeb2.__BackEnd.Dto;

public class ListarUsuarioDTO {
	private int id;
	private String nombres;
	private String apellidos;
	private String username;
	private String email;
	private int suscripcion;
	private String tema;
	private String dni;
	private String jtable;
	private String imagen;
	private int idRol;
	private String createdAt;
	private String updatedAt;

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

	public int getSuscripcion() {
		return suscripcion;
	}

	public void setSuscripcion(int suscripcion) {
		this.suscripcion = suscripcion;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public String getJtable() {
		return jtable;
	}

	public void setJtable(String jtable) {
		this.jtable = jtable;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "ListarUsuarioDTO [id=" + id + ", nombres=" + nombres + ", apellidos=" + apellidos + ", username="
				+ username + ", email=" + email + ", suscripcion=" + suscripcion + ", tema=" + tema + ", jtable="
				+ jtable + ", imagen=" + imagen + ", idRol=" + idRol + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + "]";
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

}
