package Proyecto.MegaWeb2.__BackEnd.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  @Autowired
  private JavaMailSender mailSender;

  @Async // 👈 ejecuta en segundo plano
  public void enviarCorreo(String destino, String asunto, String mensaje) {
    try {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(destino);
        mail.setSubject(asunto);
        mail.setText(mensaje);
        mailSender.send(mail);
    } catch (Exception e) {
        // Opcional: loguear error sin romper el endpoint
        System.err.println("Error enviando correo: " + e.getMessage());
    }
  }
}
