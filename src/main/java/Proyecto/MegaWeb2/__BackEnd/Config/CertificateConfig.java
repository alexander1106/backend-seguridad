package Proyecto.MegaWeb2.__BackEnd.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "certificate")
public class CertificateConfig {
    private String basePath;
    private String certFile;
    private String keyFile;
    private String password;
    private String outputDir;

    // Getters y Setters
    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    // ... otros getters y setters
}