package com.floresdelvalle.floristeria.servicio;

import com.floresdelvalle.floristeria.modelo.Entrega;
import com.floresdelvalle.floristeria.repositorio.EntregaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EntregaService {
    private final EntregaRepository entregaRepository;
    public EntregaService(EntregaRepository entregaRepository) { this.entregaRepository = entregaRepository; }
    @Transactional(readOnly = true) public List<Entrega> listar() { return entregaRepository.findAll(); }
    @Transactional(readOnly = true) public long contarPendientes() { return entregaRepository.countByEstado(Entrega.Estado.PROGRAMADA) + entregaRepository.countByEstado(Entrega.Estado.EN_RUTA); }
    @Transactional(readOnly = true) @SuppressWarnings("null") public Entrega buscarPorId(Long id) { return entregaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("La entrega no existe")); }
    @Transactional(readOnly = true) public Entrega obtenerPorId(Long id) { return buscarPorId(id); }
    @Transactional(readOnly = true) public List<Entrega> listarPorEstado(Entrega.Estado estado) { if (estado == null) throw new IllegalArgumentException("El estado es obligatorio"); return entregaRepository.findByEstado(estado); }
    @Transactional @SuppressWarnings("null") public Entrega guardar(Entrega entrega) { return entregaRepository.save(entrega); }
    @Transactional public Entrega crear(Entrega entrega) { if (entrega == null) throw new IllegalArgumentException("La entrega es obligatoria"); validarPedidoDisponible(entrega); entrega.setId(null); return guardar(entrega); }
    @Transactional public Entrega actualizar(Long id, Entrega datos) { Entrega entrega = buscarPorId(id); if (datos == null) throw new IllegalArgumentException("Los datos de la entrega son obligatorios"); if (!entrega.getPedido().getId().equals(datos.getPedido().getId()) && entregaRepository.existsByPedidoId(datos.getPedido().getId())) throw new IllegalArgumentException("El pedido ya tiene una entrega programada"); entrega.setPedido(datos.getPedido()); entrega.setConductor(datos.getConductor()); entrega.setDireccionEntrega(datos.getDireccionEntrega()); entrega.setFechaProgramada(datos.getFechaProgramada()); entrega.setEstado(datos.getEstado()); entrega.setObservaciones(datos.getObservaciones()); return guardar(entrega); }
    @Transactional public void eliminar(Long id) { entregaRepository.delete(buscarPorId(id)); }
    @Transactional public void cambiarEstado(Long id, Entrega.Estado estado) { Entrega entrega = buscarPorId(id); entrega.setEstado(estado); entregaRepository.save(entrega); }
    private void validarPedidoDisponible(Entrega entrega) { if (entrega.getPedido() == null || entrega.getPedido().getId() == null) throw new IllegalArgumentException("Selecciona un pedido"); if (entregaRepository.existsByPedidoId(entrega.getPedido().getId())) throw new IllegalArgumentException("El pedido ya tiene una entrega programada"); }
}
