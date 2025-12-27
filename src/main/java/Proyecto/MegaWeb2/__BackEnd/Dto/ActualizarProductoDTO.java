package Proyecto.MegaWeb2.__BackEnd.Dto;

public class ActualizarProductoDTO {
	private int id;
	private String nombre;
	private String descripcion;
	private String imagen;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	@Override
	public String toString() {
		return "ActualizarProductoDTO [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", imagen="
				+ imagen + "]";
	}

}
