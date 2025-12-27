package Proyecto.MegaWeb2.__BackEnd.Dto;

import java.util.List;

public class VistaConsultaDTO {
    private String tituloConsulta;
    private String nombreProducto;
    private String descripcionConsulta;
    private List<PasoDTO> pasos;


    public String getTituloConsulta() {
        return tituloConsulta;
    }

    public void setTituloConsulta(String tituloConsulta) {
        this.tituloConsulta = tituloConsulta;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcionConsulta() {
        return descripcionConsulta;
    }

    public void setDescripcionConsulta(String descripcionConsulta) {
        this.descripcionConsulta = descripcionConsulta;
    }

    public List<PasoDTO> getPasos() {
        return pasos;
    }

    public void setPasos(List<PasoDTO> pasos) {
        this.pasos = pasos;
    }

    public static class PasoDTO {
        private String tituloPaso;
        private String descripcionPaso;
        private String imagenPaso;

        public String getTituloPaso() {
            return tituloPaso;
        }

        public void setTituloPaso(String tituloPaso) {
            this.tituloPaso = tituloPaso;
        }

        public String getDescripcionPaso() {
            return descripcionPaso;
        }

        public void setDescripcionPaso(String descripcionPaso) {
            this.descripcionPaso = descripcionPaso;
        }

        public String getImagenPaso() {
            return imagenPaso;
        }

        public void setImagenPaso(String imagenPaso) {
            this.imagenPaso = imagenPaso;
        }
    }
}
