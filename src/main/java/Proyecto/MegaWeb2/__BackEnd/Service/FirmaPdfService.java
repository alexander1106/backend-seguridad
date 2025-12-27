package Proyecto.MegaWeb2.__BackEnd.Service;

import Proyecto.MegaWeb2.__BackEnd.Config.CertificateConfig;
import Proyecto.MegaWeb2.__BackEnd.Security.JwtUtil;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.graphics.PdfFont;
import com.spire.pdf.graphics.PdfFontFamily;
import com.spire.pdf.graphics.PdfFontStyle;
import com.spire.pdf.graphics.PdfImage;
import com.spire.pdf.security.GraphicMode;
import com.spire.pdf.security.PdfCertificate;
import com.spire.pdf.security.PdfCertificationFlags;
import com.spire.pdf.security.PdfSignature;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.Map;

@Service
public class FirmaPdfService {

    private final CertificateConfig certConfig;
    private final GeneradorCertificadoService certificadoService;
    private final JwtUtil jwtUtil;

    @Value("${certificado.ruta-pfx:certificado_firma.pfx}")
    private String rutaPFX;

    @Value("${certificado.password:tuPasswordSeguro}")
    private String passwordCertificado;


    @Autowired
    public FirmaPdfService(CertificateConfig certConfig, GeneradorCertificadoService certificadoService, JwtUtil jwtUtil) {
        this.certConfig = certConfig;
        this.certificadoService = certificadoService;
        this.jwtUtil = jwtUtil;
    }

    public byte[] firmarPdf(byte[] archivoPdf, String token) throws Exception {

        // Verificar y generar certificado si no existe
        if (!new java.io.File(rutaPFX).exists()) {
            certificadoService.generarCertificadoPFX(rutaPFX, passwordCertificado);
        }

        String nombreUsuario = jwtUtil.extractUsername(token);
        String nombres = (String) jwtUtil.extractAllClaims(token).get("nombres");
        String apellidos = (String) jwtUtil.extractAllClaims(token).get("apellidos");

        PdfDocument doc = new PdfDocument();
        doc.loadFromBytes(archivoPdf);

        PdfCertificate cert = new PdfCertificate(rutaPFX, passwordCertificado);

        for (int i = 0; i < doc.getPages().getCount(); i++) {
            PdfSignature signature = new PdfSignature(doc, doc.getPages().get(i), cert, "MySignature_" + i);

            Rectangle2D rect = new Rectangle2D.Float();
            rect.setFrame(
                    new Point2D.Float(
                            (float) doc.getPages().get(i).getActualSize().getWidth() - 380,
                            (float) doc.getPages().get(i).getActualSize().getHeight() - 120
                    ),
                    new Dimension(350, 150)
            );
            signature.setBounds(rect);

            signature.setGraphicMode(GraphicMode.Sign_Image_And_Sign_Detail);
            signature.setNameLabel("Nombres:");
            signature.setName(nombres);
            signature.setContactInfoLabel("Usuario:");
            signature.setContactInfo(nombreUsuario);
            signature.setDateLabel("Fecha:");
            signature.setDate(new java.util.Date());
            /*signature.setLocationInfoLabel("Location:");
            signature.setLocationInfo("Florida");
            signature.setReasonLabel("Reason: ");
            signature.setReason("The certificate of this document");
            signature.setDistinguishedNameLabel("DN: ");
            signature.setDistinguishedName(cert.get_IssuerName().getName());*/
            signature.setSignDetailsFont(new PdfFont(PdfFontFamily.Helvetica, 10f, PdfFontStyle.Bold));
            signature.setDocumentPermissions(PdfCertificationFlags.Forbid_Changes);
            signature.setCertificated(true);

            // Opción: Puedes incluir imagen si deseas.
            ClassPathResource resource = new ClassPathResource("static/firmas/firma.png");
            signature.setSignImageSource(PdfImage.fromFile(resource.getFile().getAbsolutePath()));
        }


        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        doc.saveToStream(outputStream);
        doc.close();

        return outputStream.toByteArray();
    }
}