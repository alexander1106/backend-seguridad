package Proyecto.MegaWeb2.__BackEnd.Dto;

public class MultimediaProductoDTO {
	private String imagen;
	private String descripcion;

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "MultimediaProductoDTO [imagen=" + imagen + ", descripcion=" + descripcion + "]";
	}

}
