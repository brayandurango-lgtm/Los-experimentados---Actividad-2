package com.floresdelvalle.floristeria.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModuloController {
    @GetMapping("/facturacion")
    public String facturacion() { return "redirect:/facturas"; }
}
