package Proyecto.MegaWeb2.__BackEnd.Dto;

public class DocProductoDTO {
	private String document;
	private String descripcion;

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "DocProductoDTO [document=" + document + ", descripcion=" + descripcion + "]";
	}

}
