package Proyecto.MegaWeb2.__BackEnd.Util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class URLEncryptionUtil {
    
    // IMPORTANTE: Cambiar esta clave por una más segura en properties/env vars
    private static final String ENCRYPTION_KEY = "MegaWeb2024SecureKey1234567890AB";
    
    private static final String ALGORITHM = "AES";

    /**
     * Encripta un ID entero a String Base64
     * Ejemplo: 15 -> "Xy9-zRq2"
     */
    public static String encriptarId(Integer id) {
        if (id == null || id <= 0) {
            return null;
        }
        try {
            String numeroString = String.valueOf(id);
            byte[] bytesEncriptados = encriptar(numeroString.getBytes());
            return Base64.getUrlEncoder().withoutPadding().encodeToString(bytesEncriptados);
        } catch (Exception e) {
            throw new RuntimeException("Error al encriptar ID", e);
        }
    }

    /**
     * Desencripta un String Base64 a ID entero
     * Ejemplo: "Xy9-zRq2" -> 15
     */
    public static Integer desencriptarId(String idEncriptado) {
        if (idEncriptado == null || idEncriptado.isEmpty()) {
            return null;
        }
        try {
            byte[] bytesDesencriptados = desencriptar(
                Base64.getUrlDecoder().decode(idEncriptado)
            );
            return Integer.parseInt(new String(bytesDesencriptados));
        } catch (Exception e) {
            throw new RuntimeException("Error al desencriptar ID: " + idEncriptado, e);
        }
    }

    /**
     * Encripta un String cualquiera
     */
    public static String encriptarString(String texto) {
        if (texto == null || texto.isEmpty()) {
            return null;
        }
        try {
            byte[] bytesEncriptados = encriptar(texto.getBytes());
            return Base64.getUrlEncoder().withoutPadding().encodeToString(bytesEncriptados);
        } catch (Exception e) {
            throw new RuntimeException("Error al encriptar texto", e);
        }
    }

    /**
     * Desencripta un String
     */
    public static String desencriptarString(String textoEncriptado) {
        if (textoEncriptado == null || textoEncriptado.isEmpty()) {
            return null;
        }
        try {
            byte[] bytesDesencriptados = desencriptar(
                Base64.getUrlDecoder().decode(textoEncriptado)
            );
            return new String(bytesDesencriptados);
        } catch (Exception e) {
            throw new RuntimeException("Error al desencriptar texto", e);
        }
    }

    /**
     * Método privado: encriptación AES
     */
    private static byte[] encriptar(byte[] datos) throws Exception {
        SecretKey clave = generarClaveSecreta();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, clave);
        return cipher.doFinal(datos);
    }

    /**
     * Método privado: desencriptación AES
     */
    private static byte[] desencriptar(byte[] datosCifrados) throws Exception {
        SecretKey clave = generarClaveSecreta();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, clave);
        return cipher.doFinal(datosCifrados);
    }

    /**
     * Genera la clave secreta AES a partir de la clave configurada
     */
    private static SecretKey generarClaveSecreta() {
        byte[] decodedKey = Base64.getDecoder().decode(ENCRYPTION_KEY);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, ALGORITHM);
    }

    /**
     * Método auxiliar para generar una nueva clave (solo para configuración inicial)
     * NO usar en producción cada vez
     */
    public static String generarNuevaClaveSegura() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(256, new SecureRandom());
        SecretKey clave = keyGenerator.generateKey();
        return Base64.getEncoder().encodeToString(clave.getEncoded());
    }
}