package com.floresdelvalle.floristeria.controlador;

import com.floresdelvalle.floristeria.modelo.Entrega;
import com.floresdelvalle.floristeria.servicio.EntregaService;
import com.floresdelvalle.floristeria.servicio.ConductorService;
import com.floresdelvalle.floristeria.servicio.PedidoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EntregaController {
    private final EntregaService entregaService;
    private final PedidoService pedidoService;
    private final ConductorService conductorService;
    public EntregaController(EntregaService entregaService, PedidoService pedidoService, ConductorService conductorService) { this.entregaService = entregaService; this.pedidoService = pedidoService; this.conductorService = conductorService; }

    @GetMapping("/entregas")
    public String listar(Model model) { model.addAttribute("entregas", entregaService.listar()); return "entregas/lista"; }

    @GetMapping("/entregas/nueva")
    public String nueva(Model model) { prepararFormulario(model, new Entrega()); return "entregas/formulario"; }

    @PostMapping("/entregas")
    public String guardar(@Valid Entrega entrega, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) { prepararFormulario(model, entrega); return "entregas/formulario"; }
        entregaService.guardar(entrega); redirectAttributes.addFlashAttribute("mensaje", "Entrega registrada"); return "redirect:/entregas";
    }

    @GetMapping("/entregas/{id}")
    public String detalle(@PathVariable Long id, Model model) { model.addAttribute("entrega", entregaService.buscarPorId(id)); model.addAttribute("estados", Entrega.Estado.values()); return "entregas/detalle"; }

    @GetMapping("/entregas/{id}/editar")
    public String editar(@PathVariable Long id, Model model) { prepararFormulario(model, entregaService.buscarPorId(id)); return "entregas/formulario"; }

    @PostMapping("/entregas/{id}/estado")
    public String cambiarEstado(@PathVariable Long id, Entrega.Estado estado, RedirectAttributes redirectAttributes) { entregaService.cambiarEstado(id, estado); redirectAttributes.addFlashAttribute("mensaje", "Estado de entrega actualizado"); return "redirect:/entregas/" + id; }

    private void prepararFormulario(Model model, Entrega entrega) { model.addAttribute("entrega", entrega); model.addAttribute("pedidos", pedidoService.listar()); model.addAttribute("conductores", conductorService.listar()); model.addAttribute("estados", Entrega.Estado.values()); }
}
