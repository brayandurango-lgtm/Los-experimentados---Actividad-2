package com.floresdelvalle.floristeria.controlador;

import com.floresdelvalle.floristeria.modelo.Flor;
import com.floresdelvalle.floristeria.servicio.FlorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FlorController {

    private final FlorService florService;

    public FlorController(FlorService florService) {
        this.florService = florService;
    }

    @GetMapping("/flores")
    public String listar(@RequestParam(required = false) String busqueda, Model model) {
        model.addAttribute("flores", florService.listar(busqueda));
        model.addAttribute("busqueda", busqueda);
        return "flores/lista";
    }

    @GetMapping("/flores/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("flor", new Flor());
        return "flores/formulario";
    }

    @PostMapping({"/flores", "/flores/guardar"})
    public String guardar(@Valid Flor flor, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "flores/formulario";
        }
        florService.guardar(flor);
        redirectAttributes.addFlashAttribute("mensaje", "Flor agregada al catálogo");
        return "redirect:/flores";
    }

    @GetMapping({"/flores/{id}", "/flores/detalle/{id}"})
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("flor", florService.buscarPorId(id));
        return "flores/detalle";
    }

    @GetMapping({"/flores/{id}/editar", "/flores/editar/{id}"})
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("flor", florService.buscarPorId(id));
        return "flores/formulario";
    }

    @GetMapping("/flores/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        florService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Flor desactivada");
        return "redirect:/flores";
    }

    @PostMapping("/flores/{id}/desactivar")
    public String desactivar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        florService.desactivar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Flor desactivada");
        return "redirect:/flores";
    }
}
