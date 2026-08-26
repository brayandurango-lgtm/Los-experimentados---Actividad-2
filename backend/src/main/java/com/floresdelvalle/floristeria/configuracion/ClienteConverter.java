package com.floresdelvalle.floristeria.configuracion;

import com.floresdelvalle.floristeria.modelo.Cliente;
import com.floresdelvalle.floristeria.repositorio.ClienteRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class ClienteConverter implements Converter<String, Cliente> {
    private final ClienteRepository clienteRepository;

    public ClienteConverter(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    @SuppressWarnings("null")
    public Cliente convert(@NonNull String source) {
        if (source == null || source.isBlank()) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }
        Long clienteId = Long.valueOf(source);
        return clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("El cliente no existe"));
    }
}
