package Proyecto.MegaWeb2.__BackEnd.Dto;

public class VistaClienteDTO {

	private int idcliente;
	private String nombreCliente;
	private String nombreProducto;

	public int getIdcliente() {
		return idcliente;
	}

	public void setIdcliente(int idcliente) {
		this.idcliente = idcliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	@Override
	public String toString() {
		return "VistaClienteDTO [idcliente=" + idcliente + ", nombreCliente=" + nombreCliente + ", nombreProducto="
				+ nombreProducto + "]";
	}

}
