package Proyecto.MegaWeb2.__BackEnd.Dto;

public class CrearClienteDTO {
	private String empresa;
	private String descripcion;
	private String logo;
	private String nruc;
	private String telefono;
	private String contacto;
	private String nombreComercial;
	private String direccion;
	private String localidad;
	private String grupo;
	private Short showWeb;

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getNruc() {
		return nruc;
	}

	public void setNruc(String nruc) {
		this.nruc = nruc;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getNombreComercial() {
		return nombreComercial;
	}

	public void setNombreComercial(String nombreComercial) {
		this.nombreComercial = nombreComercial;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public Short getShowWeb() {
		return showWeb;
	}

	public void setShowWeb(Short showWeb) {
		this.showWeb = showWeb;
	}

	@Override
	public String toString() {
		return "CrearClienteDTO [empresa=" + empresa + ", descripcion=" + descripcion + ", logo=" + logo + ", nruc="
				+ nruc + ", telefono=" + telefono + ", contacto=" + contacto + ", nombreComercial=" + nombreComercial
				+ ", direccion=" + direccion + ", localidad=" + localidad + ", grupo=" + grupo + ", showWeb=" + showWeb
				+ "]";
	}

}
