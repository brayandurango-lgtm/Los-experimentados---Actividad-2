package com.floresdelvalle.floristeria.repositorio;

import com.floresdelvalle.floristeria.modelo.Pago;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    boolean existsByFacturaId(Long facturaId);

    @Query("select coalesce(sum(p.valor), 0) from Pago p where p.estado in ('PAGADO', 'REGISTRADO')")
    BigDecimal totalRecibido();
}
