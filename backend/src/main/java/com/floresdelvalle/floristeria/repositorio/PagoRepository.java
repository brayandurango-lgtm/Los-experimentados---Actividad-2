package com.floresdelvalle.floristeria.repositorio;

import com.floresdelvalle.floristeria.modelo.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Long> {
}
