package com.floresdelvalle.floristeria.servicio;

import com.floresdelvalle.floristeria.modelo.Pedido;
import com.floresdelvalle.floristeria.modelo.DetallePedido;
import com.floresdelvalle.floristeria.modelo.Flor;
import com.floresdelvalle.floristeria.repositorio.PedidoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final FlorService florService;
    public PedidoService(PedidoRepository pedidoRepository, FlorService florService) { this.pedidoRepository = pedidoRepository; this.florService = florService; }
    @Transactional(readOnly = true) public List<Pedido> listar() { return pedidoRepository.findAll(); }
    @Transactional(readOnly = true) public long contarEnCurso() { return pedidoRepository.countByEstado(Pedido.Estado.EN_CURSO); }
    @Transactional(readOnly = true) public long contarCompletados() { return pedidoRepository.countByEstado(Pedido.Estado.COMPLETADO); }
    @Transactional(readOnly = true) @SuppressWarnings("null") public Pedido buscarPorId(Long id) { return pedidoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("El pedido no existe")); }
    @Transactional(readOnly = true) public Pedido obtenerDetalle(Long id) { return buscarPorId(id); }
    @Transactional(readOnly = true) public List<Pedido> listarPorEstado(Pedido.Estado estado) { if (estado == null) throw new IllegalArgumentException("El estado es obligatorio"); return pedidoRepository.findByEstado(estado); }
    @Transactional(readOnly = true) public List<Pedido> listarPorCliente(com.floresdelvalle.floristeria.modelo.Cliente cliente) { if (cliente == null) throw new IllegalArgumentException("El cliente es obligatorio"); return pedidoRepository.findByCliente(cliente); }
    @Transactional @SuppressWarnings("null") public Pedido guardar(Pedido pedido) {
        if (pedido == null) throw new IllegalArgumentException("El pedido es obligatorio");
        if (pedido.getId() == null) {
            Flor flor = florService.buscarPorId(pedido.getFlorId());
            florService.registrarSalida(pedido.getFlorId(), pedido.getCantidadFlor());
            DetallePedido detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setFlor(flor);
            detalle.setCantidad(pedido.getCantidadFlor());
            detalle.setPrecioUnitario(flor.getPrecioVenta());
            pedido.getDetalles().add(detalle);
        }
        return pedidoRepository.save(pedido);
    }
    @Transactional public Pedido crear(Pedido pedido) { if (pedido != null) pedido.setId(null); return guardar(pedido); }
    @Transactional public Pedido actualizar(Long id, Pedido datos) { Pedido pedido = buscarPorId(id); if (datos == null) throw new IllegalArgumentException("Los datos del pedido son obligatorios"); pedido.setCliente(datos.getCliente()); pedido.setFechaPedido(datos.getFechaPedido()); pedido.setFechaEntrega(datos.getFechaEntrega()); pedido.setOcasion(datos.getOcasion()); pedido.setPresupuesto(datos.getPresupuesto()); pedido.setEstado(datos.getEstado()); pedido.setObservaciones(datos.getObservaciones()); pedido.setDireccionEntrega(datos.getDireccionEntrega()); pedido.setContacto(datos.getContacto()); pedido.setTipoArreglo(datos.getTipoArreglo()); return pedidoRepository.save(pedido); }
    @Transactional @SuppressWarnings("null") public void cambiarEstado(Long id, Pedido.Estado estado) { Pedido pedido = buscarPorId(id); pedido.setEstado(estado); pedidoRepository.save(pedido); }
}
