package com.floresdelvalle.floristeria.controlador;

import com.floresdelvalle.floristeria.modelo.Factura;
import com.floresdelvalle.floristeria.modelo.Pago;
import com.floresdelvalle.floristeria.servicio.FacturaService;
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
public class FacturaController {
    private final FacturaService facturaService;
    private final PedidoService pedidoService;
    public FacturaController(FacturaService facturaService, PedidoService pedidoService) { this.facturaService = facturaService; this.pedidoService = pedidoService; }

    @GetMapping("/facturas")
    public String listar(Model model) { model.addAttribute("facturas", facturaService.listar()); return "facturas/lista"; }

    @GetMapping("/facturas/nueva")
    public String nueva(Model model) { prepararFormulario(model, new Factura()); return "facturas/formulario"; }

    @PostMapping({"/facturas", "/facturas/guardar"})
    public String guardar(@Valid Factura factura, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) { prepararFormulario(model, factura); return "facturas/formulario"; }
        try {
            if (factura.getId() == null) { facturaService.crear(factura); redirectAttributes.addFlashAttribute("mensaje", "Factura registrada"); }
            else { facturaService.actualizar(factura.getId(), factura); redirectAttributes.addFlashAttribute("mensaje", "Factura actualizada correctamente"); }
        } catch (IllegalArgumentException exception) {
            bindingResult.rejectValue("pedido", "duplicado", exception.getMessage()); prepararFormulario(model, factura); return "facturas/formulario";
        }
        return "redirect:/facturas";
    }

    @PostMapping("/facturas/actualizar/{id}")
    public String actualizar(@PathVariable Long id, @Valid Factura factura, BindingResult bindingResult,
                            Model model, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) { prepararFormulario(model, factura); return "facturas/formulario"; }
        facturaService.actualizar(id, factura);
        redirectAttributes.addFlashAttribute("mensaje", "Factura actualizada correctamente");
        return "redirect:/facturas";
    }

    @GetMapping("/facturas/{id}")
    public String detalle(@PathVariable Long id, Model model) { model.addAttribute("factura", facturaService.buscarPorId(id)); model.addAttribute("pago", new Pago()); return "facturas/detalle"; }

    @PostMapping("/facturas/generar/{pedidoId}")
    public String generar(@PathVariable Long pedidoId, RedirectAttributes redirectAttributes) {
        facturaService.generarFactura(pedidoService.buscarPorId(pedidoId));
        redirectAttributes.addFlashAttribute("mensaje", "Factura generada correctamente");
        return "redirect:/facturas";
    }

    @GetMapping({"/facturas/{id}/editar", "/facturas/editar/{id}"})
    public String editar(@PathVariable Long id, Model model) { prepararFormulario(model, facturaService.buscarPorId(id)); return "facturas/formulario"; }

    @PostMapping({"/facturas/{id}/eliminar", "/facturas/eliminar/{id}"})
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            facturaService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "Factura eliminada correctamente");
        } catch (IllegalStateException | IllegalArgumentException exception) {
            redirectAttributes.addFlashAttribute("mensaje", exception.getMessage());
        }
        return "redirect:/facturas";
    }

    @PostMapping("/facturas/{id}/pagos")
    public String registrarPago(@PathVariable Long id, @Valid Pago pago, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) { redirectAttributes.addFlashAttribute("mensaje", "Revisa los datos del pago"); return "redirect:/facturas/" + id; }
        try {
            facturaService.registrarPago(id, pago);
            redirectAttributes.addFlashAttribute("mensaje", "Pago administrativo registrado");
        } catch (IllegalArgumentException | IllegalStateException exception) {
            redirectAttributes.addFlashAttribute("mensaje", exception.getMessage());
        }
        return "redirect:/facturas/" + id;
    }

    private void prepararFormulario(Model model, Factura factura) { model.addAttribute("factura", factura); model.addAttribute("pedidos", pedidoService.listar()); }
}
