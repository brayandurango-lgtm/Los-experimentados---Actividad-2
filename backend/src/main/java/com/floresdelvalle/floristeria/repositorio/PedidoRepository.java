package com.floresdelvalle.floristeria.repositorio;

import com.floresdelvalle.floristeria.modelo.Pedido;
import com.floresdelvalle.floristeria.modelo.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

	long countByEstado(Pedido.Estado estado);

	List<Pedido> findByEstado(Pedido.Estado estado);

	List<Pedido> findByFacturaIsNull();

	List<Pedido> findByCliente(Cliente cliente);

	@Query("select p from Pedido p join p.cliente c where lower(c.nombre) like lower(concat('%', :busqueda, '%')) "
			+ "or lower(p.tipoArreglo) like lower(concat('%', :busqueda, '%')) "
			+ "or lower(p.ocasion) like lower(concat('%', :busqueda, '%'))")
	List<Pedido> buscar(@Param("busqueda") String busqueda);

	@Query("select d.flor.tipo, sum(d.cantidad) from Pedido p join p.detalles d group by d.flor.tipo order by sum(d.cantidad) desc")
	List<Object[]> floresMasVendidas();

	@Query("select p.cliente.nombre, count(p) from Pedido p group by p.cliente.nombre order by count(p) desc")
	List<Object[]> clientesConMasPedidos();
}
