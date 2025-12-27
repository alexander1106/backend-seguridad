package Proyecto.MegaWeb2.__BackEnd.Dto;

public class ListarModuloDTO {
	private int idModulo;
	private String nombre;
	private String descripcion;
	private String createdAt;
	private String updatedAt;

	public int getIdModulo() {
		return idModulo;
	}

	public void setIdModulo(int idModulo) {
		this.idModulo = idModulo;
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
		return "ListarModuloDTO [idModulo=" + idModulo + ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
