package Proyecto.MegaWeb2.__BackEnd.Util;

import java.time.LocalDateTime;

public class OtpEntry {
  private String codigo;
  private LocalDateTime expiracion;

  public OtpEntry(String codigo, LocalDateTime expiracion) {
    this.codigo = codigo;
    this.expiracion = expiracion;
  }

  public String getCodigo() {
    return codigo;
  }

  public LocalDateTime getExpiracion() {
    return expiracion;
  }
}
