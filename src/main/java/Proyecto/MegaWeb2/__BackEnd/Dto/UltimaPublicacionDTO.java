package Proyecto.MegaWeb2.__BackEnd.Dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

public class UltimaPublicacionDTO {
	@Schema(hidden = true)
	private int id;
	
	
	private String titulo;
	private String descripcion;
	private LocalDateTime fecha;
	private String imagen;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	@Override
	public String toString() {
		return "UltimaPublicacionDTO [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", imagen="
				+ imagen + "]";
	}

}
