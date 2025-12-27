package Proyecto.MegaWeb2.__BackEnd.Dto;

public class PermisoRolModuloDTO {
	private int idRol;
	private int idModulo;
	private String modulo; 
	private int pView;
	private int pCreate;
	private int pUpdate;
	private int pDelete;
	private String createdAt;
	private String updatedAt;

	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public int getIdModulo() {
		return idModulo;
	}

	public void setIdModulo(int idModulo) {
		this.idModulo = idModulo;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public int getpView() {
		return pView;
	}

	public void setpView(int pView) {
		this.pView = pView;
	}

	public int getpCreate() {
		return pCreate;
	}

	public void setpCreate(int pCreate) {
		this.pCreate = pCreate;
	}

	public int getpUpdate() {
		return pUpdate;
	}

	public void setpUpdate(int pUpdate) {
		this.pUpdate = pUpdate;
	}

	public int getpDelete() {
		return pDelete;
	}

	public void setpDelete(int pDelete) {
		this.pDelete = pDelete;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public String toString() {
		return "PermisoRolModuloDTO [idRol=" + idRol + ", idModulo=" + idModulo + ", modulo=" + modulo + ", pView="
				+ pView + ", pCreate=" + pCreate + ", pUpdate=" + pUpdate + ", pDelete=" + pDelete + ", createdAt="
				+ createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
