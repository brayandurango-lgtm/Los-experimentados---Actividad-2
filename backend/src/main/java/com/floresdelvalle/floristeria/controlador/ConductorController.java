package com.floresdelvalle.floristeria.controlador;

import com.floresdelvalle.floristeria.modelo.Conductor;
import com.floresdelvalle.floristeria.servicio.ConductorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ConductorController {
    private final ConductorService conductorService;

    public ConductorController(ConductorService conductorService) {
        this.conductorService = conductorService;
    }

    @GetMapping("/conductores")
    public String listar(Model model) {
        model.addAttribute("conductores", conductorService.listar());
        return "conductores/lista";
    }

    @GetMapping("/conductores/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("conductor", new Conductor());
        return "conductores/formulario";
    }

    @GetMapping("/conductores/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("conductor", conductorService.buscarPorId(id));
        return "conductores/formulario";
    }

    @PostMapping("/conductores")
    public String guardar(@Valid Conductor conductor, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "conductores/formulario";
        }
        if (conductor.getId() == null) {
            conductorService.crear(conductor);
            redirectAttributes.addFlashAttribute("mensaje", "Conductor creado correctamente");
        } else {
            conductorService.actualizar(conductor.getId(), conductor);
            redirectAttributes.addFlashAttribute("mensaje", "Conductor actualizado correctamente");
        }
        return "redirect:/conductores";
    }

    @PostMapping("/conductores/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            conductorService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "Conductor eliminado correctamente");
        } catch (IllegalStateException exception) {
            redirectAttributes.addFlashAttribute("mensaje", exception.getMessage());
        }
        return "redirect:/conductores";
    }
}