package com.floresdelvalle.floristeria.controlador;

import com.floresdelvalle.floristeria.modelo.Cliente;
import com.floresdelvalle.floristeria.servicio.ClienteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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

    @PostMapping("/clientes")
    public String guardar(@Valid Cliente cliente, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "clientes/formulario";
        }
        clienteService.guardar(cliente);
        redirectAttributes.addFlashAttribute("mensaje", "Cliente creado correctamente");
        return "redirect:/clientes";
    }
}
