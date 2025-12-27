package Proyecto.MegaWeb2.__BackEnd.Dto;

import java.time.LocalDateTime;

public class ComentarioResponseDTO {

	private int idComentario;
	private String comentario;
	private LocalDateTime fecha;
	private String nombres;
	private String apellidos;
	private String imagenUsuario;
	private String nombreEmpresa;

	public int getIdComentario() {
		return idComentario;
	}

	public void setIdComentario(int idComentario) {
		this.idComentario = idComentario;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
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

	public String getImagenUsuario() {
		return imagenUsuario;
	}

	public void setImagenUsuario(String imagenUsuario) {
		this.imagenUsuario = imagenUsuario;
	}

	public String getNombreEmpresa() {
		return nombreEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	@Override
	public String toString() {
		return "ComentarioResponseDTO [idComentario=" + idComentario + ", comentario=" + comentario + ", fecha=" + fecha
				+ ", nombres=" + nombres + ", apellidos=" + apellidos + ", imagenUsuario=" + imagenUsuario
				+ ", nombreEmpresa=" + nombreEmpresa + "]";
	}

}
