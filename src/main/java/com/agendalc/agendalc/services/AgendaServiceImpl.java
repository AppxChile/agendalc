package com.agendalc.agendalc.services;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.dto.AgendaRequest;
import com.agendalc.agendalc.entities.Agenda;
import com.agendalc.agendalc.entities.BloqueHorario;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.repositories.AgendaRepository;
import com.agendalc.agendalc.services.interfaces.AgendaService;
import com.agendalc.agendalc.services.interfaces.BloqueHorarioService;
import com.agendalc.agendalc.services.interfaces.TramiteService;

@Service
public class AgendaServiceImpl implements AgendaService {

    private static final String MSG_AGENDA = "Agenda no encontrada con ID: ";
    private static final String MSG_BLOQUE = "Bloque horario no encontrado ";

    private final AgendaRepository agendaRepository;
    private final BloqueHorarioService bloqueHorarioService;
    private final TramiteService tramiteService;

    public AgendaServiceImpl(AgendaRepository agendaRepository, BloqueHorarioService bloqueHorarioService,
            TramiteService tramiteService) {
        this.agendaRepository = agendaRepository;
        this.bloqueHorarioService = bloqueHorarioService;
        this.tramiteService = tramiteService;
    }

    @Transactional
    @Override
    public Agenda addBloquesAHorario(Long idAgenda, List<BloqueHorario> bloquesHorarios) {
        Agenda agenda = findById(idAgenda);

        if (bloquesHorarios == null || bloquesHorarios.isEmpty()) {
            throw new IllegalArgumentException("La lista de bloques horarios está vacía o es nula");
        }

        Set<BloqueHorario> nuevosBloques = bloquesHorarios.stream()
                .map(this::getOrCreateBloque)
                .collect(Collectors.toSet());

        agenda.getBloquesHorarios().addAll(nuevosBloques);

        return agendaRepository.save(agenda);
    }

    private BloqueHorario getOrCreateBloque(BloqueHorario bloqueRequest) {
        return (bloqueRequest.getIdBloque() == null)
                ? bloqueHorarioService.save(new BloqueHorario(
                        bloqueRequest.getHoraInicio(),
                        bloqueRequest.getHoraFin(),
                        bloqueRequest.getCuposDisponibles()))
                : bloqueHorarioService.findById(bloqueRequest.getIdBloque());
    }

    @Transactional
    @Override
    public Agenda createAgenda(AgendaRequest request) {
        Tramite tramite = tramiteService.getTramiteById(request.getIdTramite());

        if (request.getBloqueHorario() == null || request.getBloqueHorario().isEmpty()) {
            throw new IllegalArgumentException("La lista de bloques horarios está vacía o es nula");
        }

        Set<BloqueHorario> bloques = request.getBloqueHorario().stream()
                .map(this::getOrCreateBloque)
                .collect(Collectors.toSet());

        Agenda agenda = new Agenda();
        agenda.setTramite(tramite);
        agenda.setFecha(request.getFecha());
        agenda.setBloquesHorarios(bloques);

        return agendaRepository.save(agenda);
    }

    @Transactional
    @Override
    public boolean deleteAgendaById(Long id) {
        if (agendaRepository.existsById(id)) {
            agendaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public Agenda deleteBloqueDeAgenda(Long idAgenda, Long idBloqueHorario) {
        Agenda agenda = findById(idAgenda);

        BloqueHorario bloqueHorario = bloqueHorarioService.findById(idBloqueHorario);

        boolean removed = agenda.getBloquesHorarios().removeIf(bh -> bh.getIdBloque().equals(idBloqueHorario));

        if (!removed) {
            throw new IllegalArgumentException("El bloque horario no está asociado con esta agenda");
        }

        bloqueHorarioService.delete(bloqueHorario);

        return agendaRepository.save(agenda);
    }

    @Override
    public List<Agenda> getAllAgendas() {
        return agendaRepository.findAll();
    }

    @Transactional
    @Override
    public Agenda updateAgenda(Long id, Agenda agendaActualizada) {
        if (agendaRepository.existsById(id)) {
            agendaActualizada.setIdAgenda(id);
            return agendaRepository.save(agendaActualizada);
        }
        return null;
    }

    @Transactional
    @Override
    public Agenda updateBloquesHorariosDeAgenda(Long idAgenda, List<BloqueHorario> bloquesHorarioActualizados) {
        Agenda agenda = findById(idAgenda);

        if (bloquesHorarioActualizados == null || bloquesHorarioActualizados.isEmpty()) {
            throw new IllegalArgumentException("La lista de bloques horarios está vacía");
        }

        Map<Long, BloqueHorario> bloquesMap = bloqueHorarioService.findAllById(
                bloquesHorarioActualizados.stream()
                        .map(BloqueHorario::getIdBloque)
                        .toList());

        for (BloqueHorario nuevoBloqueHorario : bloquesHorarioActualizados) {
            BloqueHorario bloqueHorario = bloquesMap.get(nuevoBloqueHorario.getIdBloque());

            if (bloqueHorario == null) {
                throw new IllegalArgumentException(MSG_BLOQUE + nuevoBloqueHorario.getIdBloque());
            }

            if (!agenda.getBloquesHorarios().contains(bloqueHorario)) {
                throw new IllegalArgumentException(
                        MSG_BLOQUE + nuevoBloqueHorario.getIdBloque() + " no está asociado con esta agenda");
            }

            // Actualizar valores
            bloqueHorario.setHoraInicio(nuevoBloqueHorario.getHoraInicio());
            bloqueHorario.setHoraFin(nuevoBloqueHorario.getHoraFin());
            bloqueHorario.setCuposDisponibles(nuevoBloqueHorario.getCuposDisponibles());
        }

        // Guardar todos los bloques en una sola operación
        bloqueHorarioService.saveAll(bloquesMap.values());

        return agendaRepository.save(agenda);
    }

    @Override
    public Agenda findById(Long id) {
        return agendaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(MSG_AGENDA + id));

    }

    @Override
    public Agenda save(Agenda agenda) {
        return agendaRepository.save(agenda);
    }

}
