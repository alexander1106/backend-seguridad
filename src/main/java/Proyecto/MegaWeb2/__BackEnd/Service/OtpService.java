package Proyecto.MegaWeb2.__BackEnd.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService {

    @Autowired
    private EmailService emailService;

    public void enviarCodigo(String email) {
        String codigo = generarCodigoOTP();
        String subject = "Código de Verificación OTP";
        String body = "Tu código de verificación es: " + codigo;

        try {
            emailService.enviarCorreo(email, subject, body);
        } catch (Exception e) {
            // Manejar el error si ocurre un problema al enviar el correo
            throw new RuntimeException("No se pudo enviar el código OTP.");
        }
    }

    private String generarCodigoOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Genera un código de 6 dígitos
        return String.valueOf(otp);
    }
}


