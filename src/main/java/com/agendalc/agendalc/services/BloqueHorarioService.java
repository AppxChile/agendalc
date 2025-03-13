package com.agendalc.agendalc.services;

import com.agendalc.agendalc.entities.BloqueHorario;
import com.agendalc.agendalc.repositories.BloqueHorarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BloqueHorarioService {

    private final BloqueHorarioRepository bloqueHorarioRepository;

    public BloqueHorarioService(BloqueHorarioRepository bloqueHorarioRepository) {
        this.bloqueHorarioRepository = bloqueHorarioRepository;
    }

    @Transactional
    public BloqueHorario createBloqueHorario(BloqueHorario bloqueHorario) {
        return bloqueHorarioRepository.save(bloqueHorario);
    }

    public List<BloqueHorario> getAllBloquesHorarios() {
        return bloqueHorarioRepository.findAll();
    }

    public BloqueHorario getBloqueHorarioById(Long id) {
        return bloqueHorarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Bloque horario no encontrado con ID: " + id));
    }

    @Transactional
    public BloqueHorario updateBloqueHorario(Long id, BloqueHorario bloqueHorarioActualizado) {
        BloqueHorario bloqueExistente = getBloqueHorarioById(id);

        bloqueExistente.setHoraInicio(bloqueHorarioActualizado.getHoraInicio());
        bloqueExistente.setHoraFin(bloqueHorarioActualizado.getHoraFin());
        bloqueExistente.setCuposDisponibles(bloqueHorarioActualizado.getCuposDisponibles());

        return bloqueHorarioRepository.save(bloqueExistente);
    }

    @Transactional
    public void deleteBloqueHorarioById(Long id) {
        BloqueHorario bloqueHorario = getBloqueHorarioById(id);
        bloqueHorarioRepository.delete(bloqueHorario);
    }
}
