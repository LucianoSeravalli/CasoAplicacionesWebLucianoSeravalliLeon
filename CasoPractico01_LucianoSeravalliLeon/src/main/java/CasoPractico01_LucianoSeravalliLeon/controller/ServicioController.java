package CasoPractico01_LucianoSeravalliLeon.controller;

import CasoPractico01_LucianoSeravalliLeon.domain.Servicio;
import CasoPractico01_LucianoSeravalliLeon.service.ServicioService;
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
@RequestMapping("/servicios")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("servicios", servicioService.getServicios());
        return "servicios/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("servicio", new Servicio());
        model.addAttribute("categorias", categoriaService.getCategorias());
        model.addAttribute("idCategoriaSeleccionada", null);
        return "servicios/form";
    }

    @PostMapping("/guardar")
    public String guardar(Servicio servicio,
            @RequestParam("idCategoria") Integer idCategoria,
            RedirectAttributes redirectAttributes) {

        var optCat = categoriaService.getCategoria(idCategoria);
        if (optCat.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Debe seleccionar una categoría válida.");
            return "redirect:/servicios/nuevo";
        }

        servicio.setCategoria(optCat.get());
        servicioService.save(servicio);

        redirectAttributes.addFlashAttribute("exito", "Servicio guardado correctamente.");
        return "redirect:/servicios/listado";
    }

    @GetMapping("/editar")
    public String editar(@RequestParam("idServicio") Integer idServicio,
            Model model,
            RedirectAttributes redirectAttributes) {

        var opt = servicioService.getServicio(idServicio);
        if (opt.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "No se encontró el servicio.");
            return "redirect:/servicios/listado";
        }

        Servicio servicio = opt.get();
        model.addAttribute("servicio", servicio);
        model.addAttribute("categorias", categoriaService.getCategorias());
        model.addAttribute("idCategoriaSeleccionada", servicio.getCategoria().getIdCategoria());

        return "servicios/form";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("idServicio") Integer idServicio, RedirectAttributes redirectAttributes) {
        try {
            servicioService.delete(idServicio);
            redirectAttributes.addFlashAttribute("exito", "Servicio eliminado correctamente.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/servicios/listado";
    }
}