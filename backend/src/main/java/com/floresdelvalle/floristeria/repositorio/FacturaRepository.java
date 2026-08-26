package com.floresdelvalle.floristeria.repositorio;

import com.floresdelvalle.floristeria.modelo.Factura;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
    long countByEstado(Factura.Estado estado);

    List<Factura> findByEstado(Factura.Estado estado);

    Optional<Factura> findByPedidoId(Long pedidoId);
    @Query("select coalesce(sum(f.total), 0) from Factura f where f.estado = 'PAGADA'")
    BigDecimal totalVentasPagadas();
}
