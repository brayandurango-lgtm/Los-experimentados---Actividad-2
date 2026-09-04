package com.floresdelvalle.floristeria.configuracion;

import com.floresdelvalle.floristeria.modelo.Cliente;
import com.floresdelvalle.floristeria.modelo.Conductor;
import com.floresdelvalle.floristeria.modelo.Entrega;
import com.floresdelvalle.floristeria.modelo.Pedido;
import com.floresdelvalle.floristeria.repositorio.ClienteRepository;
import com.floresdelvalle.floristeria.repositorio.ConductorRepository;
import com.floresdelvalle.floristeria.repositorio.EntregaRepository;
import com.floresdelvalle.floristeria.repositorio.PedidoRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatosDemoConfig {

    @Bean
    CommandLineRunner cargarDatosDemo(ClienteRepository clienteRepository,
                                      ConductorRepository conductorRepository,
                                      PedidoRepository pedidoRepository,
                                      EntregaRepository entregaRepository) {
        return args -> {
            if (entregaRepository.count() > 0) {
                return;
            }

            List<Cliente> clientes = clienteRepository.findAll();
            while (clientes.size() < 4) {
                clientes.add(clienteRepository.save(nuevoCliente(clientes.size() + 1)));
            }

            List<Conductor> conductores = conductorRepository.findAll();
            while (conductores.size() < 2) {
                conductores.add(conductorRepository.save(nuevoConductor(conductores.size() + 1)));
            }

            List<Pedido> pedidos = pedidoRepository.findAll();
            while (pedidos.size() < 4) {
                pedidos.add(pedidoRepository.save(nuevoPedido(clientes.get(pedidos.size() % clientes.size()), pedidos.size() + 1)));
            }

            LocalDate hoy = LocalDate.now();
            Entrega[] entregas = {
                nuevaEntrega(pedidos.get(0), conductores.get(0), hoy, Entrega.Estado.PROGRAMADA, "Entregar en recepción.", "Cliente solicita aviso antes de llegar."),
                nuevaEntrega(pedidos.get(1), conductores.get(1), hoy.plusDays(1), Entrega.Estado.EN_RUTA, "Calle 12 # 8-40, apartamento 302", "Llamar al llegar a portería."),
                nuevaEntrega(pedidos.get(2), conductores.get(0), hoy.plusDays(2), Entrega.Estado.ENTREGADA, "Carrera 25 # 16-18", "Entrega recibida por Laura."),
                nuevaEntrega(pedidos.get(3), conductores.get(1), hoy.plusDays(3), Entrega.Estado.CANCELADA, "Avenida 6 # 20-05, local 4", "Reprogramar con el cliente.")
            };

            entregaRepository.saveAll(List.of(entregas));
        };
    }

    private Cliente nuevoCliente(int numero) {
        Cliente cliente = new Cliente();
        cliente.setNombre("Cliente Demo " + numero);
        cliente.setTelefono("30000000" + numero);
        cliente.setEmail("cliente.demo" + numero + "@ejemplo.com");
        cliente.setDireccion("Calle " + (10 + numero) + " # 20-" + numero);
        return cliente;
    }

    private Conductor nuevoConductor(int numero) {
        Conductor conductor = new Conductor();
        conductor.setNombre("Conductor Demo " + numero);
        conductor.setTelefono("31000000" + numero);
        conductor.setDocumento("DOC-DEMO-" + numero);
        conductor.setEstado("ACTIVO");
        return conductor;
    }

    private Pedido nuevoPedido(Cliente cliente, int numero) {
        Pedido pedido = new Pedido();
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setCliente(cliente);
        pedido.setDireccionEntrega("Calle " + (10 + numero) + " # 20-" + numero);
        pedido.setContacto("Contacto Demo " + numero + " - 30000000" + numero);
        pedido.setTipoArreglo(numero % 2 == 0 ? "Ramo de rosas" : "Arreglo floral mixto");
        pedido.setOcasion(numero % 2 == 0 ? "Cumpleanos" : "Aniversario");
        pedido.setFechaEntrega(LocalDate.now().plusDays(numero));
        pedido.setPresupuesto(BigDecimal.valueOf(85000L + (numero * 15000L)));
        pedido.setEstado(Pedido.Estado.EN_CURSO);
        return pedido;
    }

    private Entrega nuevaEntrega(Pedido pedido, Conductor conductor, LocalDate fecha,
                                 Entrega.Estado estado, String direccion, String observaciones) {
        Entrega entrega = new Entrega();
        entrega.setPedido(pedido);
        entrega.setConductor(conductor);
        entrega.setFechaProgramada(fecha);
        entrega.setEstado(estado);
        entrega.setDireccionEntrega(direccion);
        entrega.setObservaciones(observaciones);
        return entrega;
    }
}
