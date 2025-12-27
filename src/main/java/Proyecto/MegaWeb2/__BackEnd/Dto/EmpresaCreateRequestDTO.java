package Proyecto.MegaWeb2.__BackEnd.Dto;

public class EmpresaCreateRequestDTO {

	private String presentacion;
	private String mision;
	private String vision;
	private String telefono;
	private String direccion;

	public String getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
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
		return "EmpresaCreateRequestDTO [presentacion=" + presentacion + ", mision=" + mision + ", vision=" + vision
				+ ", telefono=" + telefono + ", direccion=" + direccion + "]";
	}

}
