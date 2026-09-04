package com.floresdelvalle.floristeria.configuracion;

import com.floresdelvalle.floristeria.modelo.Pedido;
import com.floresdelvalle.floristeria.repositorio.PedidoRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PedidoConverter implements Converter<String, Pedido> {
    private final PedidoRepository pedidoRepository;
    public PedidoConverter(PedidoRepository pedidoRepository) { this.pedidoRepository = pedidoRepository; }
    @Override
    @SuppressWarnings("null")
    public Pedido convert(String source) {
        if (source == null || source.isBlank()) return null;
        Long pedidoId = Long.valueOf(source);
        return pedidoRepository.findById(pedidoId).orElse(null);
    }
}
