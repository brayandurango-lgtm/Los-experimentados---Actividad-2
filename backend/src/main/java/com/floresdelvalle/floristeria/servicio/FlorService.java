package com.floresdelvalle.floristeria.servicio;

import com.floresdelvalle.floristeria.modelo.Flor;
import com.floresdelvalle.floristeria.repositorio.DetallePedidoRepository;
import com.floresdelvalle.floristeria.repositorio.FlorRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlorService {

    private final FlorRepository florRepository;
    private final DetallePedidoRepository detallePedidoRepository;

    public FlorService(FlorRepository florRepository, DetallePedidoRepository detallePedidoRepository) {
        this.florRepository = florRepository;
        this.detallePedidoRepository = detallePedidoRepository;
    }

    @Transactional(readOnly = true)
    public List<Flor> listar(String busqueda) {
        if (busqueda == null || busqueda.isBlank()) {
            return florRepository.findAll();
        }
        String termino = busqueda.trim();
        return florRepository.findByTipoContainingIgnoreCaseOrColorContainingIgnoreCaseOrVariedadContainingIgnoreCase(
                termino, termino, termino);
    }

    @Transactional(readOnly = true)
    public List<Flor> listar() {
        return listar(null);
    }

    @Transactional(readOnly = true)
    public List<Flor> buscar(String termino) {
        return listar(termino);
    }

    @Transactional(readOnly = true)
    public long contarDisponibles() {
        return florRepository.countByEstadoAndCantidadDisponibleGreaterThan("ACTIVA", 0);
    }

    @Transactional
    public void registrarSalida(Long florId, Integer cantidad) {
        Flor flor = buscarPorId(florId);
        if (!"ACTIVA".equals(flor.getEstado()) || flor.getCantidadDisponible() < cantidad) {
            throw new IllegalArgumentException("No hay inventario suficiente para registrar este pedido");
        }
        flor.setCantidadDisponible(flor.getCantidadDisponible() - cantidad);
        florRepository.save(flor);
    }

    @Transactional(readOnly = true)
    @SuppressWarnings("null")
    public Flor buscarPorId(Long id) {
        return florRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La flor solicitada no existe"));
    }

    @Transactional(readOnly = true)
    public Flor obtenerPorId(Long id) {
        return buscarPorId(id);
    }

    @Transactional
    @SuppressWarnings("null")
    public Flor guardar(Flor flor) {
        return florRepository.save(flor);
    }

    @Transactional
    public Flor crear(Flor flor) {
        if (flor == null) {
            throw new IllegalArgumentException("La flor es obligatoria");
        }
        flor.setId(null);
        return guardar(flor);
    }

    @Transactional
    public Flor actualizar(Long id, Flor datos) {
        Flor flor = buscarPorId(id);
        if (datos == null) {
            throw new IllegalArgumentException("Los datos de la flor son obligatorios");
        }
        flor.setTipo(datos.getTipo());
        flor.setColor(datos.getColor());
        flor.setVariedad(datos.getVariedad());
        flor.setCantidadDisponible(datos.getCantidadDisponible());
        flor.setPrecioCompra(datos.getPrecioCompra());
        flor.setPrecioVenta(datos.getPrecioVenta());
        flor.setEstado(datos.getEstado());
        return guardar(flor);
    }

    @Transactional
    public void desactivar(Long id) {
        Flor flor = buscarPorId(id);
        flor.setEstado("INACTIVA");
        florRepository.save(flor);
    }

    @Transactional
    public void eliminar(Long id) {
        Flor flor = buscarPorId(id);
        if (detallePedidoRepository.existsByFlorId(id)) {
            throw new IllegalStateException("No se puede eliminar la flor porque ya está asociada a pedidos registrados");
        }
        florRepository.delete(flor);
    }
}
