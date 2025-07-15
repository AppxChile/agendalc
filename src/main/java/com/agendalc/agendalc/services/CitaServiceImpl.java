package com.agendalc.agendalc.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.dto.CitaDto;
import com.agendalc.agendalc.dto.CitaRequest;
import com.agendalc.agendalc.dto.PersonaResponse;
import com.agendalc.agendalc.dto.SolicitudCitaResponse;
import com.agendalc.agendalc.entities.Agenda;
import com.agendalc.agendalc.entities.BloqueHorario;
import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.repositories.CitaRepository;
import com.agendalc.agendalc.services.interfaces.AgendaService;
import com.agendalc.agendalc.services.interfaces.ApiMailService;
import com.agendalc.agendalc.services.interfaces.ApiPersonaService;
import com.agendalc.agendalc.services.interfaces.BloqueHorarioService;
import com.agendalc.agendalc.services.interfaces.CitaService;
import com.agendalc.agendalc.utils.RepositoryUtils;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CitaServiceImpl implements CitaService {

    private final CitaRepository citaRepository;

    private final AgendaService agendaService;

    private final BloqueHorarioService bloqueHorarioService;

    private final ApiPersonaService apiPersonaService;

    private final ApiMailService apiMailService;

    public CitaServiceImpl(CitaRepository citaRepository, AgendaService agendaService,
            BloqueHorarioService bloqueHorarioService,
            ApiPersonaService apiPersonaService,
            ApiMailService apiMailService) {
        this.citaRepository = citaRepository;
        this.agendaService = agendaService;
        this.bloqueHorarioService = bloqueHorarioService;
        this.apiPersonaService = apiPersonaService;
        this.apiMailService = apiMailService;
    }

    @Transactional
    @Override
    public CitaDto createCita(CitaRequest citaRequest) {
        Agenda agenda = agendaService.findById(citaRequest.getIdAgenda());

        BloqueHorario bloqueHorario = bloqueHorarioService.findById(citaRequest.getIdBloqueHorario());

        if (!agenda.getBloquesHorarios().contains(bloqueHorario)) {
            throw new IllegalArgumentException("El bloque horario no pertenece a la agenda seleccionada");
        }

        if (bloqueHorario.getCuposDisponibles() <= 0) {
            throw new IllegalStateException("No hay cupos disponibles en este bloque horario");
        }

        PersonaResponse persona = apiPersonaService.getPersonaInfo(citaRequest.getRut());

        if (persona == null) {
            throw new EntityNotFoundException("Persona no econtrada");

        }

        Cita cita = new Cita();
        cita.setRut(citaRequest.getRut());
        cita.setAgenda(agenda);
        cita.setBloqueHorario(bloqueHorario);

        cita = citaRepository.save(cita);

        bloqueHorario.setCuposDisponibles(bloqueHorario.getCuposDisponibles() - 1);
        bloqueHorarioService.save(bloqueHorario);

        CitaDto citaDto = new CitaDto(cita);

        String fechaFormateada = formatFecha(cita.getFechaHora().toLocalDate());

        String horaFormateada = formatHora(cita.getFechaHora());

        HashMap<String, Object> variables = new HashMap<>();

        variables.put("nombre", persona.getNombres());
        variables.put("fecha", fechaFormateada);
        variables.put("hora", horaFormateada);

        apiMailService.sendEmail(persona.getEmail(), "Agenda de hora", "cita-template", variables);

        return citaDto;
    }

    @Override
    public Cita findById(Long id) {
        return RepositoryUtils.findOrThrow(citaRepository.findById(id),
                String.format("No se encontro la cita %d ", id));

    }

    @Override
    public List<SolicitudCitaResponse> getCitaByRut(Integer rut) {
        List<Cita> citas = citaRepository.findByRut(rut);

        if (citas.isEmpty()) {
            throw new IllegalArgumentException("No hay citas para el rut");
        }

        int mesActual = LocalDate.now().getMonthValue();

        return citas.stream()
                .map(cita -> {

                    SolicitudCitaResponse dto = new SolicitudCitaResponse();

                    dto.setRut(cita.getRut());
                    dto.setFechaSolicitud(cita.getFechaHora().toLocalDate());
                    dto.setFechaAgenda(cita.getAgenda().getFecha());
                    dto.setIdBloque(cita.getBloqueHorario().getIdBloque());
                    dto.setHoraInicioBloque(cita.getBloqueHorario().getHoraInicio());
                    dto.setHoraFinBloque(cita.getBloqueHorario().getHoraFin());

                    PersonaResponse persona = apiPersonaService.getPersonaInfo(cita.getRut());

                    dto.setVrut(persona.getVrut());

                    String nombre = persona.getNombres() + " ";
                    String paterno = persona.getPaterno() + " ";
                    String materno = persona.getMaterno();

                    dto.setNombre(nombre.concat(paterno).concat(materno));

                    return dto;

                })
                .filter(dto -> dto.getFechaSolicitud().getMonthValue() == mesActual)
                .toList();
    }

    @Transactional
    @Override
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
    @Override
    public boolean deleteCitaById(Long id) {
        Optional<Cita> citaOptional = citaRepository.findById(id);
        if (citaOptional.isPresent()) {
            citaRepository.delete(citaOptional.get());
            return true;
        }
        return false;
    }

    private String formatFecha(LocalDate fecha) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return fecha.format(formatter);

    }

    private String formatHora(LocalDateTime fecha) {

        DateTimeFormatter formatHora = DateTimeFormatter.ofPattern("HH:mm");

        return fecha.toLocalTime().format(formatHora);

    }

}
