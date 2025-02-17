package com.agendalc.agendalc.services;

import com.agendalc.agendalc.dto.CitaDto;
import com.agendalc.agendalc.dto.CitaRequest;
import com.agendalc.agendalc.entities.Agenda;
import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.entities.SolicitudCita;
import com.agendalc.agendalc.entities.Cita.EstadoCita;
import com.agendalc.agendalc.repositories.AgendaRepository;
import com.agendalc.agendalc.repositories.CitaRepository;
import com.agendalc.agendalc.repositories.SolicitudCitaRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final AgendaRepository agendaRepository;
    private final SolicitudCitaRepository solicitudCitaRepository;

    public CitaService(CitaRepository citaRepository,
            AgendaRepository agendaRepository,
            SolicitudCitaRepository solicitudCitaRepository) {
        this.citaRepository = citaRepository;
        this.agendaRepository = agendaRepository;
        this.solicitudCitaRepository=solicitudCitaRepository;
    }

    // Crear una nueva cita
    @Transactional
    public CitaDto crearCita(CitaRequest citaRequest) {
        // Validar existencia de la agenda
        Agenda agenda = agendaRepository.findById(citaRequest.getIdAgenda())
            .orElseThrow(() -> new EntityNotFoundException("Agenda no encontrada"));

        // Crear Cita
        Cita cita = new Cita();
        cita.setRut(citaRequest.getRut());
        cita.setEstado(EstadoCita.PENDIENTE);
        cita.setAgenda(agenda);

        // Guardar cita en BD
        cita = citaRepository.save(cita);

        // Crear la Solicitud
        SolicitudCita solicitud = new SolicitudCita();
        solicitud.setCita(cita);
        solicitud.setFechaSolicitud(LocalDateTime.now());

        // Guardar solicitud en BD
        solicitudCitaRepository.save(solicitud);

        // Retornar DTO
        return new CitaDto(cita);
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
