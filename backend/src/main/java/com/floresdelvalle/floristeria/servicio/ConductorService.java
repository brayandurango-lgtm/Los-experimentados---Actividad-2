package com.floresdelvalle.floristeria.servicio;

import com.floresdelvalle.floristeria.modelo.Conductor;
import com.floresdelvalle.floristeria.repositorio.ConductorRepository;
import com.floresdelvalle.floristeria.repositorio.EntregaRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConductorService {

    private final ConductorRepository conductorRepository;
    private final EntregaRepository entregaRepository;

    public ConductorService(ConductorRepository conductorRepository, EntregaRepository entregaRepository) {
        this.conductorRepository = conductorRepository;
        this.entregaRepository = entregaRepository;
    }

    @Transactional(readOnly = true)
    public List<Conductor> listar() {
        return conductorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Conductor buscarPorId(Long id) {
        return conductorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("El conductor no existe"));
    }

    @Transactional
    public Conductor crear(Conductor conductor) {
        if (conductor == null) {
            throw new IllegalArgumentException("El conductor es obligatorio");
        }
        conductor.setId(null);
        return conductorRepository.save(conductor);
    }

    @Transactional
    public Conductor actualizar(Long id, Conductor datos) {
        Conductor conductor = buscarPorId(id);
        if (datos == null) {
            throw new IllegalArgumentException("Los datos del conductor son obligatorios");
        }
        conductor.setNombre(datos.getNombre());
        conductor.setTelefono(datos.getTelefono());
        conductor.setDocumento(datos.getDocumento());
        conductor.setEstado(datos.getEstado());
        return conductorRepository.save(conductor);
    }

    @Transactional
    public void eliminar(Long id) {
        Conductor conductor = buscarPorId(id);
        if (entregaRepository.existsByConductorId(id)) {
            throw new IllegalStateException("No se puede eliminar el conductor porque tiene entregas relacionadas");
        }
        conductorRepository.delete(conductor);
    }
}
