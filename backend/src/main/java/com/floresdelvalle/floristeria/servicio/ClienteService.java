package com.floresdelvalle.floristeria.servicio;

import com.floresdelvalle.floristeria.modelo.Cliente;
import com.floresdelvalle.floristeria.repositorio.ClienteRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    public ClienteService(ClienteRepository clienteRepository) { this.clienteRepository = clienteRepository; }
    @Transactional(readOnly = true) public List<Cliente> listar() { return clienteRepository.findAll(); }
    @Transactional(readOnly = true) @SuppressWarnings("null") public Cliente buscarPorId(Long id) { return clienteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("El cliente no existe")); }
    @Transactional(readOnly = true) public Cliente obtenerPorId(Long id) { return buscarPorId(id); }
    @Transactional @SuppressWarnings("null") public Cliente guardar(Cliente cliente) { return clienteRepository.save(cliente); }
    @Transactional public Cliente crear(Cliente cliente) { if (cliente == null) throw new IllegalArgumentException("El cliente es obligatorio"); cliente.setId(null); return guardar(cliente); }
    @Transactional public Cliente actualizar(Long id, Cliente datos) { Cliente cliente = buscarPorId(id); if (datos == null) throw new IllegalArgumentException("Los datos del cliente son obligatorios"); cliente.setNombre(datos.getNombre()); cliente.setTelefono(datos.getTelefono()); cliente.setEmail(datos.getEmail()); cliente.setDireccion(datos.getDireccion()); return guardar(cliente); }
    @Transactional public void eliminar(Long id) { clienteRepository.delete(buscarPorId(id)); }
}
