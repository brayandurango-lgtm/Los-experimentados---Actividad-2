package com.floresdelvalle.floristeria.servicio;

import com.floresdelvalle.floristeria.modelo.Conductor;
import com.floresdelvalle.floristeria.repositorio.ConductorRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConductorService {

    private final ConductorRepository conductorRepository;

    public ConductorService(ConductorRepository conductorRepository) {
        this.conductorRepository = conductorRepository;
    }

    @Transactional(readOnly = true)
    public List<Conductor> listar() {
        return conductorRepository.findAll();
    }
}
