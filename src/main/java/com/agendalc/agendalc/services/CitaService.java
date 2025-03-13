package com.agendalc.agendalc.services;

import com.agendalc.agendalc.dto.CitaDto;
import com.agendalc.agendalc.dto.CitaRequest;
import com.agendalc.agendalc.dto.PersonaResponse;
import com.agendalc.agendalc.entities.Agenda;
import com.agendalc.agendalc.entities.BloqueHorario;
import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.entities.SolicitudCita;
import com.agendalc.agendalc.entities.SolicitudCita.EstadoSolicitud;
import com.agendalc.agendalc.repositories.AgendaRepository;
import com.agendalc.agendalc.repositories.BloqueHorarioRepository;
import com.agendalc.agendalc.repositories.CitaRepository;
import com.agendalc.agendalc.repositories.SolicitudCitaRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Optional;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final AgendaRepository agendaRepository;
    private final SolicitudCitaRepository solicitudCitaRepository;
    private final BloqueHorarioRepository bloqueHorarioRepository;
    private final ApiService apiService;

    public CitaService(CitaRepository citaRepository,
            AgendaRepository agendaRepository,
            SolicitudCitaRepository solicitudCitaRepository,
            BloqueHorarioRepository bloqueHorarioRepository,
            ApiService apiService) {
        this.citaRepository = citaRepository;
        this.agendaRepository = agendaRepository;
        this.solicitudCitaRepository = solicitudCitaRepository;
        this.bloqueHorarioRepository = bloqueHorarioRepository;
        this.apiService = apiService;

    }

    // Crear una nueva cita
    @Transactional
    public CitaDto createCita(CitaRequest citaRequest) {
        Agenda agenda = agendaRepository.findById(citaRequest.getIdAgenda())
                .orElseThrow(() -> new EntityNotFoundException("Agenda no encontrada"));

        BloqueHorario bloqueHorario = bloqueHorarioRepository.findById(citaRequest.getIdBloqueHorario())
                .orElseThrow(() -> new EntityNotFoundException("Bloque horario no encontrado"));

        if (!agenda.getBloquesHorarios().contains(bloqueHorario)) {
            throw new IllegalArgumentException("El bloque horario no pertenece a la agenda seleccionada");
        }

        if (bloqueHorario.getCuposDisponibles() <= 0) {
            throw new IllegalStateException("No hay cupos disponibles en este bloque horario");
        }

        PersonaResponse persona = apiService.getPersonaInfo(citaRequest.getRut());

        if (persona == null) {
            throw new EntityNotFoundException("Persona no econtrada");

        }

        Cita cita = new Cita();
        cita.setRut(citaRequest.getRut());
        cita.setAgenda(agenda);
        cita.setBloqueHorario(bloqueHorario);

        cita = citaRepository.save(cita);

        bloqueHorario.setCuposDisponibles(bloqueHorario.getCuposDisponibles() - 1);
        bloqueHorarioRepository.save(bloqueHorario);

        SolicitudCita solicitud = new SolicitudCita();
        solicitud.setCita(cita);
        solicitud.setFechaSolicitud(LocalDate.now());
        solicitud.setEstado(EstadoSolicitud.PENDIENTE);

        solicitudCitaRepository.save(solicitud);

        CitaDto citaDto = new CitaDto(cita);

        String fechaFormateada = formatFecha(cita.getFechaHora().toLocalDate());

        String horaFormateada = formatHora(cita.getFechaHora());

        HashMap<String, Object> variables = new HashMap<>();

        variables.put("nombre", persona.getNombres());
        variables.put("fecha", fechaFormateada);
        variables.put("hora", horaFormateada);

        apiService.sendEmail(persona.getEmail(), "Agenda de hora", "cita-template", variables);

        return citaDto;
    }

    private String formatFecha(LocalDate fecha) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return fecha.format(formatter);

    }

    private String formatHora(LocalDateTime fecha) {

        DateTimeFormatter formatHora = DateTimeFormatter.ofPattern("HH:mm");

        return fecha.toLocalTime().format(formatHora);

    }

    public Optional<Cita> getCitaById(Long id) {
        return citaRepository.findById(id);
    }

    @Transactional
    public Cita updateCita(Long id, Cita citaActualizada) {
        Optional<Cita> citaOptional = citaRepository.findById(id);
        if (citaOptional.isPresent()) {
            Cita citaExistente = citaOptional.get();
            citaExistente.setRut(citaActualizada.getRut());
            citaExistente.setAgenda(citaActualizada.getAgenda());
            citaExistente.setFechaHora(citaActualizada.getFechaHora());
            return citaRepository.save(citaExistente);
        }
        return null;
    }

    @Transactional
    public boolean deleteCitaById(Long id) {
        Optional<Cita> citaOptional = citaRepository.findById(id);
        if (citaOptional.isPresent()) {
            citaRepository.delete(citaOptional.get());
            return true;
        }
        return false;
    }
}
