package Proyecto.MegaWeb2.__BackEnd.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginViewController {

    @GetMapping("/login")
    public String mostrarLogin() {
        return "index"; // Carga login.html desde /templates
    }
}
