package com.floresdelvalle.floristeria.repositorio;

import com.floresdelvalle.floristeria.modelo.Entrega;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    @Override
    @EntityGraph(attributePaths = {"pedido", "conductor"})
    Optional<Entrega> findById(Long id);
    long countByEstado(Entrega.Estado estado);
    List<Entrega> findByEstado(Entrega.Estado estado);
    boolean existsByPedidoId(Long pedidoId);
    boolean existsByConductorId(Long conductorId);
}
