package Proyecto.MegaWeb2.__BackEnd.Controller;

import Proyecto.MegaWeb2.__BackEnd.Service.FirmaPdfService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/firma")
@Tag(name = "Firmas", description = "Firma digital PDF con Apache PDFBox y BouncyCastle")
public class FirmaController {

    @Autowired
    private FirmaPdfService firmaPdfService;


    public FirmaController() {
        try {
            //generarCertificadoSiNoExiste();
        } catch (Exception e) {
            throw new RuntimeException("Error generando certificado", e);
        }
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> firmarPdf(
            @RequestPart("file") MultipartFile archivo,
            @RequestHeader("Authorization") String authHeader) {
        try {
            if (archivo.isEmpty()) {
                return ResponseEntity.badRequest().body("Debe subir un archivo PDF".getBytes());
            }

            if (!archivo.getContentType().equalsIgnoreCase("application/pdf")) {
                return ResponseEntity.badRequest().body("El archivo debe ser PDF".getBytes());
            }
            if (archivo.getSize() > 5 * 1024 * 1024) {
                return ResponseEntity.badRequest().body("El archivo no debe superar los 5 MB".getBytes());
            }

            String token = authHeader.replace("Bearer ", "");

            byte[] pdfFirmado = firmaPdfService.firmarPdf(archivo.getBytes(), token);

            return ResponseEntity.ok()
                    .header("Content-Type", "application/pdf")
                    .header("Content-Disposition", "attachment; filename=\"documento_firmado.pdf\"")
                    .body(pdfFirmado);

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(("Error al firmar el documento: " + e.getMessage()).getBytes());
        }
    }
}
