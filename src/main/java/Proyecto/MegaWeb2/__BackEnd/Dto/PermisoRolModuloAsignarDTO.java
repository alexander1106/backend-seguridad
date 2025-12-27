package Proyecto.MegaWeb2.__BackEnd.Dto;
import io.swagger.v3.oas.annotations.media.Schema;
public class PermisoRolModuloAsignarDTO {
	@Schema(hidden = true)
	private int idRol;
	
	
	
	private int idModulo;
	private int pView;
	private int pCreate;
	private int pUpdate;
	private int pDelete;
	
	
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
	@Override
	public String toString() {
		return "PermisoRolModuloAsignarDTO [idRol=" + idRol + ", idModulo=" + idModulo + ", pView=" + pView
				+ ", pCreate=" + pCreate + ", pUpdate=" + pUpdate + ", pDelete=" + pDelete + "]";
	}


	
	

}
