package Proyecto.MegaWeb2.__BackEnd.Dto;

public class RolPermisosUpdateDTO {
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
		return "RolPermisosUpdateDTO [idRol=" + idRol + ", nombre=" + nombre + "]";
	}

	
}
