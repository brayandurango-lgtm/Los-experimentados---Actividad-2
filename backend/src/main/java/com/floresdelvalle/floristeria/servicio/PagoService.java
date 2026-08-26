package com.floresdelvalle.floristeria.servicio;

import com.floresdelvalle.floristeria.modelo.Factura;
import com.floresdelvalle.floristeria.modelo.Pago;
import com.floresdelvalle.floristeria.repositorio.PagoRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    @Transactional(readOnly = true)
    public List<Pago> listar() {
        return pagoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Pago obtenerPorId(Long id) {
        return pagoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El pago no existe"));
    }

    @Transactional
    public Pago registrar(Factura factura, Pago pago) {
        if (factura == null) {
            throw new IllegalArgumentException("La factura es obligatoria");
        }
        if (pago == null) {
            throw new IllegalArgumentException("El pago es obligatorio");
        }
        pago.setId(null);
        pago.setFactura(factura);
        return pagoRepository.save(pago);
    }

    @Transactional
    public Pago crear(Pago pago) {
        if (pago == null || pago.getFactura() == null) {
            throw new IllegalArgumentException("El pago y su factura son obligatorios");
        }
        return registrar(pago.getFactura(), pago);
    }
}
