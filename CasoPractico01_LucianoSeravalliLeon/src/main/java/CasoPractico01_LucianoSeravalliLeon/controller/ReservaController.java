package CasoPractico01_LucianoSeravalliLeon.controller;

import CasoPractico01_LucianoSeravalliLeon.domain.Reserva;
import CasoPractico01_LucianoSeravalliLeon.service.ReservaService;
import CasoPractico01_LucianoSeravalliLeon.service.ServicioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ServicioService servicioService;

    @GetMapping("/listado")
    public String listado(Model model) {
        model.addAttribute("reservas", reservaService.getReservas());
        return "reservas/listado";
    }

    @GetMapping("/nuevo")
public String nuevo(Model model) {
    model.addAttribute("reserva", new Reserva());
    model.addAttribute("servicios", servicioService.getServicios());
    model.addAttribute("idServicioSeleccionado", null);
    return "reservas/form";
}

@PostMapping("/guardar")
public String guardar(Reserva reserva,
                      @RequestParam("idServicio") Integer idServicio,
                      RedirectAttributes redirectAttributes) {

    var optServ = servicioService.getServicio(idServicio);
    if (optServ.isEmpty()) {
        redirectAttributes.addFlashAttribute("error", "Debe seleccionar un servicio válido.");
        return "redirect:/reservas/nuevo";
    }

    reserva.setServicio(optServ.get());
    reservaService.save(reserva);

    redirectAttributes.addFlashAttribute("exito", "Reserva guardada correctamente.");
    return "redirect:/reservas/listado";
}

@GetMapping("/editar")
public String editar(@RequestParam("idReserva") Integer idReserva,
                     Model model,
                     RedirectAttributes redirectAttributes) {

    var opt = reservaService.getReserva(idReserva);
    if (opt.isEmpty()) {
        redirectAttributes.addFlashAttribute("error", "No se encontró la reserva.");
        return "redirect:/reservas/listado";
    }

    Reserva reserva = opt.get();
    model.addAttribute("reserva", reserva);
    model.addAttribute("servicios", servicioService.getServicios());
    model.addAttribute("idServicioSeleccionado", reserva.getServicio().getIdServicio());

    return "reservas/form";
}

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam("idReserva") Integer idReserva, RedirectAttributes redirectAttributes) {
        try {
            reservaService.delete(idReserva);
            redirectAttributes.addFlashAttribute("exito", "Reserva eliminada correctamente.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/reservas/listado";
    }
}