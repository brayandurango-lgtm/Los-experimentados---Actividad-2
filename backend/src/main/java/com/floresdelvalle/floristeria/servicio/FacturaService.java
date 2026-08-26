package com.floresdelvalle.floristeria.servicio;

import com.floresdelvalle.floristeria.modelo.Factura;
import com.floresdelvalle.floristeria.modelo.Pago;
import com.floresdelvalle.floristeria.modelo.Pedido;
import com.floresdelvalle.floristeria.repositorio.FacturaRepository;
import com.floresdelvalle.floristeria.repositorio.PagoRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacturaService {
    private final FacturaRepository facturaRepository;
    private final PagoService pagoService;
    public FacturaService(FacturaRepository facturaRepository, PagoService pagoService) { this.facturaRepository = facturaRepository; this.pagoService = pagoService; }
    @Transactional(readOnly = true) public List<Factura> listar() { return facturaRepository.findAll(); }
    @Transactional(readOnly = true) public long contarPendientes() { return facturaRepository.countByEstado(Factura.Estado.PENDIENTE); }
    @Transactional(readOnly = true) @SuppressWarnings("null") public Factura buscarPorId(Long id) { return facturaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("La factura no existe")); }
    @Transactional(readOnly = true) public Factura obtenerPorId(Long id) { return buscarPorId(id); }
    @Transactional(readOnly = true) public List<Factura> listarPorEstado(Factura.Estado estado) { if (estado == null) throw new IllegalArgumentException("El estado es obligatorio"); return facturaRepository.findByEstado(estado); }
    @Transactional public Factura guardar(Factura factura) { if (factura.getCostosAdicionales() == null) factura.setCostosAdicionales(BigDecimal.ZERO); factura.setTotal(factura.getSubtotal().add(factura.getCostosAdicionales())); return facturaRepository.save(factura); }
    @Transactional public Factura generarParaPedidoCompletado(Pedido pedido) { if (pedido == null || pedido.getId() == null) throw new IllegalArgumentException("El pedido es obligatorio"); if (pedido.getEstado() != Pedido.Estado.COMPLETADO) throw new IllegalStateException("Solo se puede facturar un pedido completado"); return facturaRepository.findByPedidoId(pedido.getId()).orElseGet(() -> { Factura factura = new Factura(); factura.setPedido(pedido); BigDecimal subtotal = pedido.getDetalles().stream().map(detalle -> detalle.getSubtotal() == null ? detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad())) : detalle.getSubtotal()).reduce(BigDecimal.ZERO, BigDecimal::add); factura.setSubtotal(subtotal); return guardar(factura); }); }
    @Transactional public Factura generarFactura(Pedido pedido) { return generarParaPedidoCompletado(pedido); }
    @Transactional public Pago registrarPago(Long facturaId, Pago pago) { Factura factura = buscarPorId(facturaId); if (pago == null) throw new IllegalArgumentException("El pago es obligatorio"); if (factura.getEstado() == Factura.Estado.ANULADA) throw new IllegalStateException("No se puede pagar una factura anulada"); Pago guardado = pagoService.registrar(factura, pago); factura.setEstado(Factura.Estado.PAGADA); facturaRepository.save(factura); return guardado; }
    @Transactional(readOnly = true) public BigDecimal totalVentasPagadas() { return facturaRepository.totalVentasPagadas(); }
}
