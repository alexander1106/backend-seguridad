package Proyecto.MegaWeb2.__BackEnd.Service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.springframework.stereotype.Service;

@Service
public class TwoFactorService {

    private final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    // 🔹 Genera un secreto único para el usuario
    public String generateSecret() {
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        return key.getKey();
    }

    // 🔹 Genera la URL compatible con Google Authenticator
    public String getOtpAuthURL(String username, String secret) {
        String issuer = "MegaWeb"; // Nombre de tu app
        return String.format(
                "otpauth://totp/%s:%s?secret=%s&issuer=%s",
                issuer, username, secret, issuer
        );
    }

    // 🔹 Verifica si el código ingresado por el usuario es válido
    public boolean verifyCode(String secret, int code) {
        return gAuth.authorize(secret, code);
    }
    
}
