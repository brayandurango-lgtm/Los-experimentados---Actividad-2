package com.floresdelvalle.floristeria.controlador;

import com.floresdelvalle.floristeria.modelo.Cliente;
import com.floresdelvalle.floristeria.servicio.ClienteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ClienteController {
    private final ClienteService clienteService;
    public ClienteController(ClienteService clienteService) { this.clienteService = clienteService; }

    @GetMapping("/clientes")
    public String listar(Model model) {
        model.addAttribute("clientes", clienteService.listar());
        return "clientes/lista";
    }

    @GetMapping("/clientes/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "clientes/formulario";
    }

    @PostMapping({"/clientes", "/clientes/guardar"})
    public String guardar(@Valid Cliente cliente, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "clientes/formulario";
        }
        if (cliente.getId() == null) {
            clienteService.crear(cliente);
            redirectAttributes.addFlashAttribute("mensaje", "Cliente creado correctamente");
        } else {
            clienteService.actualizar(cliente.getId(), cliente);
            redirectAttributes.addFlashAttribute("mensaje", "Cliente actualizado correctamente");
        }
        return "redirect:/clientes";
    }

    @PostMapping("/clientes/actualizar/{id}")
    public String actualizar(@PathVariable Long id, @Valid Cliente cliente, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "clientes/formulario";
        }
        clienteService.actualizar(id, cliente);
        redirectAttributes.addFlashAttribute("mensaje", "Cliente actualizado correctamente");
        return "redirect:/clientes";
    }

    @GetMapping({"/clientes/{id}/editar", "/clientes/editar/{id}"})
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("cliente", clienteService.buscarPorId(id));
        return "clientes/formulario";
    }

    @PostMapping({"/clientes/{id}/eliminar", "/clientes/eliminar/{id}"})
    public String eliminar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            clienteService.eliminar(id);
            redirectAttributes.addFlashAttribute("mensaje", "Cliente eliminado correctamente");
        } catch (IllegalStateException exception) {
            redirectAttributes.addFlashAttribute("mensaje", exception.getMessage());
        }
        return "redirect:/clientes";
    }
}
