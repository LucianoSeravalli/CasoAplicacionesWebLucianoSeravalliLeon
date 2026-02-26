package CasoPractico01_LucianoSeravalliLeon.controller;

import CasoPractico01_LucianoSeravalliLeon.domain.Categoria;
import CasoPractico01_LucianoSeravalliLeon.service.CategoriaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("categorias", categoriaService.getCategorias());
        return "categorias/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/form";
    }

    @PostMapping("/guardar")
    public String guardar(Categoria categoria, RedirectAttributes redirectAttributes) {
        categoriaService.save(categoria);
        redirectAttributes.addFlashAttribute("exito", "Categoría guardada correctamente.");
        return "redirect:/categorias/listado";
    }

    @GetMapping("/editar")
    public String editar(@RequestParam("idCategoria") Integer idCategoria, Model model, RedirectAttributes redirectAttributes) {
        var opt = categoriaService.getCategoria(idCategoria);
        if (opt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "No se encontró la categoría.");
            return "redirect:/categorias/listado";
        }
        model.addAttribute("categoria", opt.get());
        return "categorias/form";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("idCategoria") Integer idCategoria, RedirectAttributes redirectAttributes) {
        try {
            categoriaService.delete(idCategoria);
            redirectAttributes.addFlashAttribute("exito", "Categoría eliminada correctamente.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/categorias/listado";
    }
}