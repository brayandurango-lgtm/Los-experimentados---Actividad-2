package com.floresdelvalle.floristeria.configuracion;

import com.floresdelvalle.floristeria.modelo.Cliente;
import com.floresdelvalle.floristeria.repositorio.ClienteRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ClienteConverter implements Converter<String, Cliente> {
    private final ClienteRepository clienteRepository;

    public ClienteConverter(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    @SuppressWarnings("null")
    public Cliente convert(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }
        Long clienteId = Long.valueOf(source);
        return clienteRepository.findById(clienteId).orElse(null);
    }
}
