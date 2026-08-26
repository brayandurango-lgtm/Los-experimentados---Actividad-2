package com.floresdelvalle.floristeria.controlador;

import com.floresdelvalle.floristeria.servicio.ReporteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReporteController {
    private final ReporteService reporteService;
    public ReporteController(ReporteService reporteService) { this.reporteService = reporteService; }

    @GetMapping("/reportes")
    public String index(Model model) {
        model.addAttribute("cantidadFlores", reporteService.cantidadFlores());
        model.addAttribute("totalVentas", reporteService.ingresosGenerados());
        model.addAttribute("cantidadPedidos", reporteService.cantidadPedidos());
        model.addAttribute("facturasPendientes", reporteService.facturasPendientes());
        model.addAttribute("pedidosPorEstado", reporteService.pedidosPorEstado());
        model.addAttribute("entregasPorEstado", reporteService.entregasPorEstado());
        model.addAttribute("floresMasVendidas", reporteService.floresMasVendidas());
        model.addAttribute("clientesConMasPedidos", reporteService.clientesConMasPedidos());
        return "reportes/index";
    }
}
