package com.agendalc.agendalc.services;

import com.agendalc.agendalc.controllers.dto.AgendaRequest;
import com.agendalc.agendalc.entities.Agenda;
import com.agendalc.agendalc.entities.BloqueHorario;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.repositories.AgendaRepository;
import com.agendalc.agendalc.repositories.BloqueHorarioRepository;
import com.agendalc.agendalc.repositories.TramiteRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AgendaService {

    
    private final AgendaRepository agendaRepository;
    private final TramiteRepository tramiteRepository;
    private final BloqueHorarioRepository bloqueHorarioRepository;

    public AgendaService(AgendaRepository agendaRepository, TramiteRepository tramiteRepository, BloqueHorarioRepository bloqueHorarioRepository){
        this.agendaRepository = agendaRepository;
        this.tramiteRepository=tramiteRepository;
        this.bloqueHorarioRepository=bloqueHorarioRepository;
    }

    @Transactional
    public Agenda crearAgenda(AgendaRequest request) {


        Tramite tramite = tramiteRepository.findById(request.getIdTramite())
            .orElseThrow(() -> new RuntimeException("Trámite no encontrado"));

        BloqueHorario bloqueHorario = bloqueHorarioRepository.findById(request.getIdBloque())
            .orElseThrow(() -> new RuntimeException("Bloque horario no encontrado"));  // 🔹 Aquí se obtiene el bloque

        Agenda agenda = new Agenda();
        agenda.setTramite(tramite);
        agenda.setBloqueHorario(bloqueHorario);  // 🔹 Aseguramos que no sea null
        agenda.setFecha(request.getFecha());
        agenda.setCuposDisponibles(request.getCuposDisponibles());

        return agendaRepository.save(agenda);
    }

    // Obtener todas las agendas
    public List<Agenda> obtenerTodasLasAgendas() {
        return agendaRepository.findAll();
    }

    // Obtener una agenda por su ID
    public Optional<Agenda> obtenerAgendaPorId(Long id) {
        return agendaRepository.findById(id);
    }

    @Transactional
    public Agenda actualizarAgenda(Long id, Agenda agendaActualizada) {
        if (agendaRepository.existsById(id)) {
            agendaActualizada.setIdAgenda(id);
            return agendaRepository.save(agendaActualizada);
        }
        return null;
    }

    @Transactional
    public boolean eliminarAgenda(Long id) {
        if (agendaRepository.existsById(id)) {
            agendaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
