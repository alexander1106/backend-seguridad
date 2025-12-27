package Proyecto.MegaWeb2.__BackEnd.Service;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;

@Service
public class GeneradorCertificadoService {

    public void generarCertificadoPFX(String rutaPFX, String password) throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        // 1. Generar par de claves
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA", "BC");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // 2. Crear certificado autofirmado
        X509Certificate certificado = generarCertificadoAutofirmado(keyPair);

        // 3. Crear almacén PKCS12 (PFX)
        KeyStore pkcs12 = KeyStore.getInstance("PKCS12", "BC");
        pkcs12.load(null, null);
        pkcs12.setKeyEntry("certificado-firma", keyPair.getPrivate(), password.toCharArray(),
                new java.security.cert.Certificate[]{certificado});

        // 4. Guardar archivo PFX
        try (FileOutputStream fos = new FileOutputStream(rutaPFX)) {
            pkcs12.store(fos, password.toCharArray());
        }
    }

    private X509Certificate generarCertificadoAutofirmado(KeyPair keyPair) throws Exception {
        // Usamos Bouncy Castle para generar el certificado
        org.bouncycastle.x509.X509V3CertificateGenerator certGen = new org.bouncycastle.x509.X509V3CertificateGenerator();

        // Configurar detalles del certificado
        certGen.setSerialNumber(java.math.BigInteger.valueOf(System.currentTimeMillis()));
        certGen.setIssuerDN(new javax.security.auth.x500.X500Principal("CN=TuEmpresa, O=TuOrganizacion, C=PE"));
        certGen.setNotBefore(new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24));
        certGen.setNotAfter(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365)); // 1 año de validez
        certGen.setSubjectDN(new javax.security.auth.x500.X500Principal("CN=TuEmpresa, O=TuOrganizacion, C=PE"));
        certGen.setPublicKey(keyPair.getPublic());
        certGen.setSignatureAlgorithm("SHA256WithRSAEncryption");

        return certGen.generate(keyPair.getPrivate(), "BC");
    }
}
