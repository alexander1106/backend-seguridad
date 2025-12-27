package Proyecto.MegaWeb2.__BackEnd.Dto;

public class LicenciaClienteDTO {

	private int idCliente;
	private String nombreCliente;
	private int idProducto;
	private String nombreProducto;
	private String fechasetup;
	private String fechasupdate;
	private String versionupdate;
	private String licseriehd;
	private String liccpuid;
	private Integer licnumusuario;
	private String licnumserie;
	private int licencia;
	private String fecha_i;
	private String fecha_f;
	private double monto;
	private int estadolicencia;

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public int getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getFechasetup() {
		return fechasetup;
	}

	public void setFechasetup(String fechasetup) {
		this.fechasetup = fechasetup;
	}

	public String getFechasupdate() {
		return fechasupdate;
	}

	public void setFechasupdate(String fechasupdate) {
		this.fechasupdate = fechasupdate;
	}

	public String getVersionupdate() {
		return versionupdate;
	}

	public void setVersionupdate(String versionupdate) {
		this.versionupdate = versionupdate;
	}

	public String getLicseriehd() {
		return licseriehd;
	}

	public void setLicseriehd(String licseriehd) {
		this.licseriehd = licseriehd;
	}

	public String getLiccpuid() {
		return liccpuid;
	}

	public void setLiccpuid(String liccpuid) {
		this.liccpuid = liccpuid;
	}

	public Integer getLicnumusuario() {
		return licnumusuario;
	}

	public void setLicnumusuario(Integer licnumusuario) {
		this.licnumusuario = licnumusuario;
	}

	public String getLicnumserie() {
		return licnumserie;
	}

	public void setLicnumserie(String licnumserie) {
		this.licnumserie = licnumserie;
	}

	public int getLicencia() {
		return licencia;
	}

	public void setLicencia(int licencia) {
		this.licencia = licencia;
	}

	public String getFecha_i() {
		return fecha_i;
	}

	public void setFecha_i(String fecha_i) {
		this.fecha_i = fecha_i;
	}

	public String getFecha_f() {
		return fecha_f;
	}

	public void setFecha_f(String fecha_f) {
		this.fecha_f = fecha_f;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public int getEstadolicencia() {
		return estadolicencia;
	}

	public void setEstadolicencia(int estadolicencia) {
		this.estadolicencia = estadolicencia;
	}

	@Override
	public String toString() {
		return "LicenciaClienteDTO [idCliente=" + idCliente + ", nombreCliente=" + nombreCliente + ", idProducto="
				+ idProducto + ", nombreProducto=" + nombreProducto + ", fechasetup=" + fechasetup + ", fechasupdate="
				+ fechasupdate + ", versionupdate=" + versionupdate + ", licseriehd=" + licseriehd + ", liccpuid="
				+ liccpuid + ", licnumusuario=" + licnumusuario + ", licnumserie=" + licnumserie + ", licencia="
				+ licencia + ", fecha_i=" + fecha_i + ", fecha_f=" + fecha_f + ", monto=" + monto + ", estadolicencia="
				+ estadolicencia + "]";
	}

}
