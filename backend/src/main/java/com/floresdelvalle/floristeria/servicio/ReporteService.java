package com.floresdelvalle.floristeria.servicio;

import com.floresdelvalle.floristeria.modelo.Entrega;
import com.floresdelvalle.floristeria.modelo.Factura;
import com.floresdelvalle.floristeria.modelo.Flor;
import com.floresdelvalle.floristeria.modelo.Pedido;
import com.floresdelvalle.floristeria.repositorio.EntregaRepository;
import com.floresdelvalle.floristeria.repositorio.FacturaRepository;
import com.floresdelvalle.floristeria.repositorio.PedidoRepository;
import com.floresdelvalle.floristeria.repositorio.FlorRepository;
import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReporteService {
    private final PedidoRepository pedidoRepository;
    private final EntregaRepository entregaRepository;
    private final FacturaRepository facturaRepository;
    private final FlorRepository florRepository;
    public ReporteService(PedidoRepository pedidoRepository, EntregaRepository entregaRepository, FacturaRepository facturaRepository, FlorRepository florRepository) { this.pedidoRepository = pedidoRepository; this.entregaRepository = entregaRepository; this.facturaRepository = facturaRepository; this.florRepository = florRepository; }
    @Transactional(readOnly = true) public long cantidadFlores() { return florRepository.count(); }
    @Transactional(readOnly = true) public long cantidadPedidos() { return pedidoRepository.count(); }
    @Transactional(readOnly = true) public BigDecimal ingresosGenerados() { return facturaRepository.totalVentasPagadas(); }
    @Transactional(readOnly = true) public long facturasPendientes() { return facturaRepository.countByEstado(Factura.Estado.PENDIENTE); }
    @Transactional(readOnly = true) public Map<Pedido.Estado, Long> pedidosPorEstado() { Map<Pedido.Estado, Long> resultado = new EnumMap<>(Pedido.Estado.class); for (Pedido.Estado estado : Pedido.Estado.values()) resultado.put(estado, pedidoRepository.countByEstado(estado)); return resultado; }
    @Transactional(readOnly = true) public Map<Entrega.Estado, Long> entregasPorEstado() { Map<Entrega.Estado, Long> resultado = new EnumMap<>(Entrega.Estado.class); for (Entrega.Estado estado : Entrega.Estado.values()) resultado.put(estado, entregaRepository.countByEstado(estado)); return resultado; }
    @Transactional(readOnly = true) public List<Object[]> floresMasVendidas() { return pedidoRepository.floresMasVendidas(); }
    @Transactional(readOnly = true) public List<Object[]> clientesConMasPedidos() { return pedidoRepository.clientesConMasPedidos(); }
}
