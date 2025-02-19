package com.agendalc.agendalc.services;

import com.agendalc.agendalc.entities.BloqueHorario;
import com.agendalc.agendalc.repositories.BloqueHorarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BloqueHorarioService {

    private final BloqueHorarioRepository bloqueHorarioRepository;

    public BloqueHorarioService(BloqueHorarioRepository bloqueHorarioRepository){
        this.bloqueHorarioRepository=bloqueHorarioRepository;
    }

    @Transactional
    public BloqueHorario crearBloqueHorario(BloqueHorario bloqueHorario) {
        return bloqueHorarioRepository.save(bloqueHorario);
    }

    public List<BloqueHorario> obtenerTodosLosBloquesHorarios() {
        return bloqueHorarioRepository.findAll();
    }

    public Optional<BloqueHorario> obtenerBloqueHorarioPorId(Long id) {
        return bloqueHorarioRepository.findById(id);
    }

    @Transactional
    public BloqueHorario actualizarBloqueHorario(Long id, BloqueHorario bloqueHorarioActualizado) {
        if (bloqueHorarioRepository.existsById(id)) {
            bloqueHorarioActualizado.setIdBloque(id);
            return bloqueHorarioRepository.save(bloqueHorarioActualizado);
        }
        return null;
    }

    @Transactional
    public boolean eliminarBloqueHorario(Long id) {
        if (bloqueHorarioRepository.existsById(id)) {
            bloqueHorarioRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
