package Proyecto.MegaWeb2.__BackEnd.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Proyecto.MegaWeb2.__BackEnd.Service.EmailService;

@RestController
@RequestMapping("/api/test")
public class TestCorreoController {

  @Autowired
  private EmailService emailService;

  @GetMapping("/enviar")
  public ResponseEntity<?> enviarCorreo() {
    emailService.enviarCorreo(
      "helard.g640@gmail.com",   // cámbialo por tu correo
      "Correo de prueba",
      "¡Este es un mensaje de prueba desde Spring Boot!"
    );
    return ResponseEntity.ok("Correo enviado correctamente.");
  }
}
