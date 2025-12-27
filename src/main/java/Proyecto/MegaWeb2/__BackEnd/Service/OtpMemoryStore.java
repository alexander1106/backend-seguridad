package Proyecto.MegaWeb2.__BackEnd.Service;

import Proyecto.MegaWeb2.__BackEnd.Util.OtpEntry;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpMemoryStore {

  private final Map<String, OtpEntry> otpMap = new ConcurrentHashMap<>();

  public void guardarCodigo(String email, String codigo) {
    String clave = email.trim().toLowerCase(); // 🔧 Normalizar correo
    otpMap.put(clave, new OtpEntry(codigo, LocalDateTime.now().plusMinutes(5)));
  }

  public boolean verificarCodigo(String email, String codigoIngresado) {
    String clave = email.trim().toLowerCase(); // 🔧 Normalizar también al verificar
    OtpEntry entry = otpMap.get(clave);
    if (entry == null) return false;
    if (LocalDateTime.now().isAfter(entry.getExpiracion())) {
      otpMap.remove(clave);
      return false;
    }
    boolean valido = entry.getCodigo().equals(codigoIngresado);
    if (valido) otpMap.remove(clave);
    return valido;
  }
}
