package com.floresdelvalle.floristeria.configuracion;

import com.floresdelvalle.floristeria.modelo.Pedido;
import com.floresdelvalle.floristeria.repositorio.PedidoRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class PedidoConverter implements Converter<String, Pedido> {
    private final PedidoRepository pedidoRepository;
    public PedidoConverter(PedidoRepository pedidoRepository) { this.pedidoRepository = pedidoRepository; }
    @Override
    @SuppressWarnings("null")
    public Pedido convert(@NonNull String source) {
        if (source.isBlank()) throw new IllegalArgumentException("El pedido es obligatorio");
        Long pedidoId = Long.valueOf(source);
        return pedidoRepository.findById(pedidoId).orElseThrow(() -> new IllegalArgumentException("El pedido no existe"));
    }
}
