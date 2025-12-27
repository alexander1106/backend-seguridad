package Proyecto.MegaWeb2.__BackEnd.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class RolDTO {
	@Schema(hidden = true)
	private int idRol;

	private String nombre;

	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "RolDTO [idRol=" + idRol + ", nombre=" + nombre + "]";
	}

}
