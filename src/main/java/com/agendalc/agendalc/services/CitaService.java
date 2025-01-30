package com.agendalc.agendalc.services;

import com.agendalc.agendalc.controllers.dto.CitaRequest;
import com.agendalc.agendalc.entities.Agenda;
import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.entities.Cita.EstadoCita;
import com.agendalc.agendalc.repositories.AgendaRepository;
import com.agendalc.agendalc.repositories.CitaRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final AgendaRepository agendaRepository;

    public CitaService(CitaRepository citaRepository,
            AgendaRepository agendaRepository) {
        this.citaRepository = citaRepository;
        this.agendaRepository = agendaRepository;
    }

    // Crear una nueva cita
    @Transactional
    public Cita crearCita(CitaRequest citaRequest) {

        if (citaRequest == null) {
            throw new IllegalArgumentException("La cita no puede ser nula.");
        }

        // Obtienes la agenda y verificas si existe
        Agenda agenda = agendaRepository.findById(citaRequest.getIdAgenda())
                .orElseThrow(() -> new EntityNotFoundException(
                        "La agenda con ID " + citaRequest.getIdAgenda() + " no existe."));

        Cita cita = new Cita();
        cita.setRut(citaRequest.getRut());
        cita.setEstado(EstadoCita.PENDIENTE);
        cita.setAgenda(agenda); // La relación se establece aquí

        // Guarda la cita
        return citaRepository.save(cita);
    }

    // Obtener una cita por su id
    public Optional<Cita> obtenerCita(Long id) {
        return citaRepository.findById(id);
    }

    @Transactional
    public Cita actualizarCita(Long id, Cita citaActualizada) {
        Optional<Cita> citaOptional = citaRepository.findById(id);
        if (citaOptional.isPresent()) {
            Cita citaExistente = citaOptional.get();
            citaExistente.setRut(citaActualizada.getRut());
            citaExistente.setAgenda(citaActualizada.getAgenda());
            citaExistente.setFechaHora(citaActualizada.getFechaHora());
            citaExistente.setEstado(citaActualizada.getEstado());
            return citaRepository.save(citaExistente);
        }
        return null; // o lanzar una excepción si no se encuentra la cita
    }

    @Transactional
    public boolean eliminarCita(Long id) {
        Optional<Cita> citaOptional = citaRepository.findById(id);
        if (citaOptional.isPresent()) {
            citaRepository.delete(citaOptional.get());
            return true;
        }
        return false; // o lanzar una excepción si no se encuentra la cita
    }
}
