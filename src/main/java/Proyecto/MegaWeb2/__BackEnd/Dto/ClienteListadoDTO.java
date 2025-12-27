package Proyecto.MegaWeb2.__BackEnd.Dto;

public class ClienteListadoDTO {
	private int id;
	private String nombreCliente;
	private String imagenCliente;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getImagenCliente() {
		return imagenCliente;
	}

	public void setImagenCliente(String imagenCliente) {
		this.imagenCliente = imagenCliente;
	}

	@Override
	public String toString() {
		return "ClienteListadoDTO [id=" + id + ", nombreCliente=" + nombreCliente + ", imagenCliente=" + imagenCliente
				+ "]";
	}

}
