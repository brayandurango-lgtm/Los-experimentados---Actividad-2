package com.floresdelvalle.floristeria;

import com.floresdelvalle.floristeria.modelo.Cliente;
import com.floresdelvalle.floristeria.modelo.Conductor;
import com.floresdelvalle.floristeria.repositorio.ClienteRepository;
import com.floresdelvalle.floristeria.repositorio.ConductorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class FloristeriaApplication {

    public static void main(String[] args) {
        SpringApplication.run(FloristeriaApplication.class, args);
    }

    @Bean
    public CommandLineRunner datosIniciales(ClienteRepository clienteRepository, ConductorRepository conductorRepository) {
        return args -> {
            if (clienteRepository.count() == 0) {
                clienteRepository.saveAll(List.of(
                        crearCliente("Masculino", "3000000001", "cliente1@floresdelvalle.local", "Dirección del cliente 1"),
                        crearCliente("Femenina", "3000000002", "cliente2@floresdelvalle.local", "Dirección del cliente 2"),
                        crearCliente("Otro", "3000000003", "cliente3@floresdelvalle.local", "Dirección del cliente 3")
                ));
            } else {
                clienteRepository.renombrar("Cliente 1", "Masculino");
                clienteRepository.renombrar("Cliente 2", "Femenina");
                clienteRepository.renombrar("Cliente 3", "Otro");
            }
            if (conductorRepository.count() == 0) {
                conductorRepository.saveAll(List.of(
                        crearConductor("Domicilio vehiculo terrestre", "3000000011", "TERRESTRE-1"),
                        crearConductor("Domicilio vehiculo acuatico", "3000000012", "ACUATICO-1"),
                        crearConductor("Domicilio vehiculo aereo", "3000000013", "AEREO-1")
                ));
            }
        };
    }

    private static Cliente crearCliente(String nombre, String telefono, String email, String direccion) {
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setTelefono(telefono);
        cliente.setEmail(email);
        cliente.setDireccion(direccion);
        return cliente;
    }

    private static Conductor crearConductor(String nombre, String telefono, String documento) {
        Conductor conductor = new Conductor();
        conductor.setNombre(nombre);
        conductor.setTelefono(telefono);
        conductor.setDocumento(documento);
        conductor.setEstado("ACTIVO");
        return conductor;
    }
}
