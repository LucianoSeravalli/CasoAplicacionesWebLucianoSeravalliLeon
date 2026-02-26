package CasoPractico01_LucianoSeravalliLeon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ContactoController {

    @GetMapping("/contacto")
    public String contacto() {
        return "contacto";
    }

    @PostMapping("/contacto/enviar")
    public String enviar(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("exito",
                "¡Mensaje enviado correctamente!");
        return "redirect:/";
    }
}
