package Proyecto.MegaWeb2.__BackEnd.Dto;

import java.util.List;

public class VistaProductoDTO {
	private int id;
	private String nombre;
	private String descripcion;
	private String imagen;
	private String caracteristicas;
	private String video;

	private List<DocProductoDTO> documentacion;
	private List<MultimediaProductoDTO> multimedia;

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

	public String getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(String caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public List<DocProductoDTO> getDocumentacion() {
		return documentacion;
	}

	public void setDocumentacion(List<DocProductoDTO> documentacion) {
		this.documentacion = documentacion;
	}

	public List<MultimediaProductoDTO> getMultimedia() {
		return multimedia;
	}

	public void setMultimedia(List<MultimediaProductoDTO> multimedia) {
		this.multimedia = multimedia;
	}

	@Override
	public String toString() {
		return "VistaProductoDTO [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", imagen="
				+ imagen + ", caracteristicas=" + caracteristicas + ", video=" + video + ", documentacion="
				+ documentacion + ", multimedia=" + multimedia + "]";
	}

}
