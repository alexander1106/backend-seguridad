package Proyecto.MegaWeb2.__BackEnd.Dto;

public class NosotrosDTO {
	private String descripcion;
	private String mision;
	private String vision;
	private String telefono;
	private String direccion;

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMision() {
		return mision;
	}

	public void setMision(String mision) {
		this.mision = mision;
	}

	public String getVision() {
		return vision;
	}

	public void setVision(String vision) {
		this.vision = vision;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public String toString() {
		return "NosotrosDTO [descripcion=" + descripcion + ", mision=" + mision + ", vision=" + vision + ", telefono="
				+ telefono + ", direccion=" + direccion + "]";
	}

}
