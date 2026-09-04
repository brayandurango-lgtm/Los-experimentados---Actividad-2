package com.floresdelvalle.floristeria.servicio;

import com.floresdelvalle.floristeria.modelo.Factura;
import com.floresdelvalle.floristeria.modelo.Pago;
import com.floresdelvalle.floristeria.modelo.Pedido;
import com.floresdelvalle.floristeria.repositorio.FacturaRepository;
import com.floresdelvalle.floristeria.repositorio.PagoRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FacturaService {
    private final FacturaRepository facturaRepository;
    private final PagoRepository pagoRepository;
    private final PagoService pagoService;

    public FacturaService(FacturaRepository facturaRepository, PagoRepository pagoRepository, PagoService pagoService) {
        this.facturaRepository = facturaRepository;
        this.pagoRepository = pagoRepository;
        this.pagoService = pagoService;
    }

    @Transactional(readOnly = true) public List<Factura> listar() { return facturaRepository.findAll(); }
    @Transactional(readOnly = true) public long contarPendientes() { return facturaRepository.countByEstado(Factura.Estado.PENDIENTE); }
    @Transactional(readOnly = true) @SuppressWarnings("null") public Factura buscarPorId(Long id) { return facturaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("La factura no existe")); }
    @Transactional(readOnly = true) public Factura obtenerPorId(Long id) { return buscarPorId(id); }
    @Transactional(readOnly = true) public List<Factura> listarPorEstado(Factura.Estado estado) { if (estado == null) throw new IllegalArgumentException("El estado es obligatorio"); return facturaRepository.findByEstado(estado); }
    @Transactional public Factura guardar(Factura factura) { if (factura == null || factura.getPedido() == null) throw new IllegalArgumentException("La factura y el pedido son obligatorios"); if (factura.getSubtotal() == null) throw new IllegalArgumentException("El subtotal es obligatorio"); if (factura.getCostosAdicionales() == null) factura.setCostosAdicionales(BigDecimal.ZERO); factura.setTotal(factura.getSubtotal().add(factura.getCostosAdicionales())); return facturaRepository.save(factura); }
    @Transactional public Factura crear(Factura factura) { if (factura == null) throw new IllegalArgumentException("La factura es obligatoria"); if (factura.getPedido() == null || facturaRepository.existsByPedidoId(factura.getPedido().getId())) throw new IllegalArgumentException("El pedido ya tiene una factura"); factura.setId(null); if (factura.getNumeroFactura() == null || factura.getNumeroFactura().isBlank()) factura.setNumeroFactura(generarNumeroFactura()); return guardar(factura); }
    @Transactional public Factura actualizar(Long id, Factura datos) { Factura factura = buscarPorId(id); if (datos == null) throw new IllegalArgumentException("Los datos de la factura son obligatorios"); if (datos.getPedido() == null) throw new IllegalArgumentException("Selecciona un pedido"); if (!factura.getPedido().getId().equals(datos.getPedido().getId()) && facturaRepository.existsByPedidoId(datos.getPedido().getId())) throw new IllegalArgumentException("El pedido ya tiene una factura"); factura.setNumeroFactura(datos.getNumeroFactura()); factura.setPedido(datos.getPedido()); factura.setSubtotal(datos.getSubtotal()); factura.setCostosAdicionales(datos.getCostosAdicionales()); factura.setFecha(datos.getFecha()); return guardar(factura); }
    @Transactional public void eliminar(Long id) {
        Factura factura = buscarPorId(id);
        if (pagoRepository.existsByFacturaId(id)) {
            throw new IllegalStateException("No se puede eliminar la factura porque tiene pagos registrados");
        }
        if (factura.getPedido() != null) {
            factura.getPedido().setFactura(null);
        }
        facturaRepository.delete(factura);
    }
    @Transactional public Factura generarParaPedidoCompletado(Pedido pedido) { if (pedido == null || pedido.getId() == null) throw new IllegalArgumentException("El pedido es obligatorio"); if (pedido.getEstado() != Pedido.Estado.COMPLETADO) throw new IllegalStateException("Solo se puede facturar un pedido completado"); return facturaRepository.findByPedidoId(pedido.getId()).orElseGet(() -> { Factura factura = new Factura(); factura.setPedido(pedido); BigDecimal subtotal = pedido.getDetalles().stream().map(detalle -> detalle.getSubtotal() == null ? detalle.getPrecioUnitario().multiply(BigDecimal.valueOf(detalle.getCantidad())) : detalle.getSubtotal()).reduce(BigDecimal.ZERO, BigDecimal::add); factura.setSubtotal(subtotal); return guardar(factura); }); }
    @Transactional public Factura generarFactura(Pedido pedido) { return generarParaPedidoCompletado(pedido); }
    @Transactional public Pago registrarPago(Long facturaId, Pago pago) { Factura factura = buscarPorId(facturaId); if (pago == null) throw new IllegalArgumentException("El pago es obligatorio"); if (factura.getEstado() == Factura.Estado.ANULADA) throw new IllegalStateException("No se puede pagar una factura anulada"); Pago guardado = pagoService.registrar(factura, pago); if (pago.getEstado() == Pago.Estado.PAGADO || pago.getEstado() == Pago.Estado.REGISTRADO) { BigDecimal totalPagado = factura.getPagos().stream().filter(item -> item.getEstado() == Pago.Estado.PAGADO || item.getEstado() == Pago.Estado.REGISTRADO).map(Pago::getValor).reduce(BigDecimal.ZERO, BigDecimal::add); if (totalPagado.compareTo(factura.getTotal()) >= 0) factura.setEstado(Factura.Estado.PAGADA); } facturaRepository.save(factura); return guardado; }
    @Transactional(readOnly = true) public BigDecimal totalVentasPagadas() { return facturaRepository.totalVentasPagadas(); }
    private String generarNumeroFactura() { return "FAC-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase(); }
}
