package com.floresdelvalle.floristeria.repositorio;

import com.floresdelvalle.floristeria.modelo.Flor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlorRepository extends JpaRepository<Flor, Long> {

	long countByEstado(String estado);

	long countByEstadoAndCantidadDisponibleGreaterThan(String estado, Integer cantidad);

	List<Flor> findByTipoContainingIgnoreCase(String tipo);

	List<Flor> findByVariedadContainingIgnoreCase(String variedad);

	List<Flor> findByTipoContainingIgnoreCaseOrColorContainingIgnoreCaseOrVariedadContainingIgnoreCase(
			String tipo, String color, String variedad);
}
