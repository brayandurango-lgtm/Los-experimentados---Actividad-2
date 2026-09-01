package com.floresdelvalle.floristeria.repositorio;

import com.floresdelvalle.floristeria.modelo.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {
    boolean existsByFlorId(Long florId);
}
