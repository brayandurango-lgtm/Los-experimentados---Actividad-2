package com.floresdelvalle.floristeria.controlador;

import com.floresdelvalle.floristeria.modelo.Pedido;
import com.floresdelvalle.floristeria.servicio.ClienteService;
import com.floresdelvalle.floristeria.servicio.FlorService;
import com.floresdelvalle.floristeria.servicio.PedidoService;
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
public class PedidoController {
    private final PedidoService pedidoService;
    private final ClienteService clienteService;
    private final FlorService florService;

    public PedidoController(PedidoService pedidoService, ClienteService clienteService, FlorService florService) {
        this.pedidoService = pedidoService;
        this.clienteService = clienteService;
        this.florService = florService;
    }

    @GetMapping("/pedidos")
    public String listar(@RequestParam(required = false) String busqueda, Model model) {
        model.addAttribute("pedidos", pedidoService.listar(busqueda));
        model.addAttribute("busqueda", busqueda);
        return "pedidos/lista";
    }

    @GetMapping("/pedidos/nuevo")
    public String nuevo(Model model) {
        prepararFormulario(model, new Pedido());
        return "pedidos/formulario";
    }

    @PostMapping({"/pedidos", "/pedidos/guardar"})
    public String guardar(@Valid Pedido pedido, BindingResult bindingResult,
                          Model model, RedirectAttributes redirectAttributes) {
        if (pedido.getId() == null) {
            if (pedido.getFlorId() == null) {
                bindingResult.rejectValue("florId", "required", "Selecciona una flor");
            }
            if (pedido.getCantidadFlor() == null || pedido.getCantidadFlor() <= 0) {
                bindingResult.rejectValue("cantidadFlor", "positive", "La cantidad debe ser mayor que cero");
            }
        }
        if (bindingResult.hasErrors()) {
            prepararFormulario(model, pedido);
            return "pedidos/formulario";
        }
        if (pedido.getId() == null) {
            pedidoService.crear(pedido);
            redirectAttributes.addFlashAttribute("mensaje", "Pedido creado correctamente");
        } else {
            pedidoService.actualizar(pedido.getId(), pedido);
            redirectAttributes.addFlashAttribute("mensaje", "Pedido actualizado correctamente");
        }
        return "redirect:/pedidos";
    }

    @PostMapping("/pedidos/actualizar/{id}")
    public String actualizar(@PathVariable Long id, @Valid Pedido pedido, BindingResult bindingResult,
                            Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            prepararFormulario(model, pedido);
            return "pedidos/formulario";
        }
        pedidoService.actualizar(id, pedido);
        redirectAttributes.addFlashAttribute("mensaje", "Pedido actualizado correctamente");
        return "redirect:/pedidos";
    }

    @GetMapping("/pedidos/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("pedido", pedidoService.buscarPorId(id));
        return "pedidos/detalle";
    }

    @GetMapping({"/pedidos/{id}/editar", "/pedidos/editar/{id}"})
    public String editar(@PathVariable Long id, Model model) {
        prepararFormulario(model, pedidoService.buscarPorId(id));
        return "pedidos/formulario";
    }

    @PostMapping("/pedidos/{id}/estado")
    public String cambiarEstado(@PathVariable Long id, Pedido.Estado estado,
                                RedirectAttributes redirectAttributes) {
        pedidoService.cambiarEstado(id, estado);
        redirectAttributes.addFlashAttribute("mensaje", "Estado del pedido actualizado");
        return "redirect:/pedidos/" + id;
    }

    @PostMapping("/pedidos/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        pedidoService.eliminar(id);
        redirectAttributes.addFlashAttribute("mensaje", "Pedido eliminado correctamente");
        return "redirect:/pedidos";
    }

    private void prepararFormulario(Model model, Pedido pedido) {
        model.addAttribute("pedido", pedido);
        model.addAttribute("clientes", clienteService.listar());
        model.addAttribute("flores", florService.listar(null));
        model.addAttribute("estados", Pedido.Estado.values());
    }
}
