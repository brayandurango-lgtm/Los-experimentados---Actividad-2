package com.floresdelvalle.floristeria.controlador;

import com.floresdelvalle.floristeria.servicio.EntregaService;
import com.floresdelvalle.floristeria.servicio.FacturaService;
import com.floresdelvalle.floristeria.servicio.FlorService;
import com.floresdelvalle.floristeria.servicio.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {
    private final FlorService florService;
    private final PedidoService pedidoService;
    private final EntregaService entregaService;
    private final FacturaService facturaService;

    public DashboardController(FlorService florService, PedidoService pedidoService, EntregaService entregaService, FacturaService facturaService) {
        this.florService = florService;
        this.pedidoService = pedidoService;
        this.entregaService = entregaService;
        this.facturaService = facturaService;
    }

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        model.addAttribute("floresDisponibles", florService.contarDisponibles());
        model.addAttribute("pedidosEnCurso", pedidoService.contarEnCurso());
        model.addAttribute("pedidosCompletados", pedidoService.contarCompletados());
        model.addAttribute("entregasPendientes", entregaService.contarPendientes());
        model.addAttribute("ventas", facturaService.totalVentasPagadas());
        model.addAttribute("facturasPendientes", facturaService.contarPendientes());
        return "dashboard";
    }
}
