package CasoPractico01_LucianoSeravalliLeon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class OpinionController {

    @PostMapping("/opiniones/enviar")
    public String enviarOpinion(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("exito", "¡Gracias por tu opinión!");
        return "redirect:/";
    }
}