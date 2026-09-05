package com.floresdelvalle.floristeria.repositorio;

import com.floresdelvalle.floristeria.modelo.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	boolean existsByPedidosClienteId(Long clienteId);

	List<Cliente> findTop50ByNombreContainingIgnoreCaseOrderByNombreAsc(String nombre);

	@Modifying
	@Transactional
	@Query("update Cliente c set c.nombre = :nuevo where c.nombre = :anterior")
	int renombrar(@Param("anterior") String anterior, @Param("nuevo") String nuevo);
}
