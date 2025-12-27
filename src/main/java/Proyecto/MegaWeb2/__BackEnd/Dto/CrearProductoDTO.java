package Proyecto.MegaWeb2.__BackEnd.Dto;

public class CrearProductoDTO {

	private String nombre;
	private String descripcion;
	private String imagen;
	private String caracteristicas;
	private String descarga;
	private String video;
	private String caja;
	private String titdescarga;
	private String ultimaversion;

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

	public String getDescarga() {
		return descarga;
	}

	public void setDescarga(String descarga) {
		this.descarga = descarga;
	}

	public String getVideo() {
		return video;
	}

	public void setVideo(String video) {
		this.video = video;
	}

	public String getCaja() {
		return caja;
	}

	public void setCaja(String caja) {
		this.caja = caja;
	}

	public String getTitdescarga() {
		return titdescarga;
	}

	public void setTitdescarga(String titdescarga) {
		this.titdescarga = titdescarga;
	}

	public String getUltimaversion() {
		return ultimaversion;
	}

	public void setUltimaversion(String ultimaversion) {
		this.ultimaversion = ultimaversion;
	}

	@Override
	public String toString() {
		return "CrearProductoDTO [nombre=" + nombre + ", descripcion=" + descripcion + ", imagen=" + imagen
				+ ", caracteristicas=" + caracteristicas + ", descarga=" + descarga + ", video=" + video + ", caja="
				+ caja + ", titdescarga=" + titdescarga + ", ultimaversion=" + ultimaversion + "]";
	}

}
