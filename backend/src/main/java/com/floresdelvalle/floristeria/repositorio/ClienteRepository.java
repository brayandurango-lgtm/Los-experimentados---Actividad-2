package com.floresdelvalle.floristeria.repositorio;

import com.floresdelvalle.floristeria.modelo.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	boolean existsByPedidosClienteId(Long clienteId);
}
