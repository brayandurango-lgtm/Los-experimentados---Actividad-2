package com.floresdelvalle.floristeria.repositorio;

import com.floresdelvalle.floristeria.modelo.Entrega;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntregaRepository extends JpaRepository<Entrega, Long> {
    long countByEstado(Entrega.Estado estado);
    List<Entrega> findByEstado(Entrega.Estado estado);
}
