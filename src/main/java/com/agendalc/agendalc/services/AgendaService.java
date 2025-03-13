package com.agendalc.agendalc.services;

import com.agendalc.agendalc.dto.AgendaRequest;
import com.agendalc.agendalc.entities.Agenda;
import com.agendalc.agendalc.entities.BloqueHorario;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.repositories.AgendaRepository;
import com.agendalc.agendalc.repositories.BloqueHorarioRepository;
import com.agendalc.agendalc.repositories.TramiteRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AgendaService {

    private static final String MSG_AGENDA = "Agenda no encontrada con ID: ";
    private static final String MSG_BLOQUE = "Bloque horario no encontrado ";

    private final AgendaRepository agendaRepository;
    private final TramiteRepository tramiteRepository;
    private final BloqueHorarioRepository bloqueHorarioRepository;

    public AgendaService(AgendaRepository agendaRepository, TramiteRepository tramiteRepository,
            BloqueHorarioRepository bloqueHorarioRepository) {
        this.agendaRepository = agendaRepository;
        this.tramiteRepository = tramiteRepository;
        this.bloqueHorarioRepository = bloqueHorarioRepository;
    }

    @Transactional
    public Agenda createAgenda(AgendaRequest request) {

        Tramite tramite = tramiteRepository.findById(request.getIdTramite())
                .orElseThrow(() -> new RuntimeException("Trámite no encontrado"));

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

    public List<Agenda> getAllAgendas() {
        return agendaRepository.findAll();
    }

    private BloqueHorario getOrCreateBloque(BloqueHorario bloqueRequest) {
        return (bloqueRequest.getIdBloque() == null)
                ? bloqueHorarioRepository.save(new BloqueHorario(
                        bloqueRequest.getHoraInicio(),
                        bloqueRequest.getHoraFin(),
                        bloqueRequest.getCuposDisponibles()))
                : bloqueHorarioRepository.findById(bloqueRequest.getIdBloque())
                        .orElseThrow(() -> new RuntimeException("Bloque horario no encontrado"));
    }

    public Optional<Agenda> getAgendaById(Long id) {
        return agendaRepository.findById(id);
    }

    @Transactional
    public Agenda updateAgenda(Long id, Agenda agendaActualizada) {
        if (agendaRepository.existsById(id)) {
            agendaActualizada.setIdAgenda(id);
            return agendaRepository.save(agendaActualizada);
        }
        return null;
    }

    @Transactional
    public boolean deleteAgendaById(Long id) {
        if (agendaRepository.existsById(id)) {
            agendaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Agenda addBloquesAHorario(Long idAgenda, List<BloqueHorario> bloquesHorarios) {
        Agenda agenda = agendaRepository.findById(idAgenda)
                .orElseThrow(() -> new IllegalArgumentException(MSG_AGENDA + idAgenda));

        if (bloquesHorarios == null || bloquesHorarios.isEmpty()) {
            throw new IllegalArgumentException("La lista de bloques horarios está vacía o es nula");
        }

        // Usamos stream() para procesar la lista de bloques y agregar los nuevos o
        // existentes
        Set<BloqueHorario> nuevosBloques = bloquesHorarios.stream()
                .map(this::getOrCreateBloque)
                .collect(Collectors.toSet());

        // Agregar los nuevos bloques a la agenda
        agenda.getBloquesHorarios().addAll(nuevosBloques);

        return agendaRepository.save(agenda);
    }

    @Transactional
    public Agenda deleteBloqueDeAgenda(Long idAgenda, Long idBloqueHorario) {
        Agenda agenda = agendaRepository.findById(idAgenda)
                .orElseThrow(() -> new IllegalArgumentException(MSG_AGENDA + idAgenda));

        BloqueHorario bloqueHorario = bloqueHorarioRepository.findById(idBloqueHorario)
                .orElseThrow(() -> new IllegalArgumentException(MSG_BLOQUE + idBloqueHorario));

        // Eliminamos el bloque de la agenda usando removeIf (más eficiente)
        boolean removed = agenda.getBloquesHorarios().removeIf(bh -> bh.getIdBloque().equals(idBloqueHorario));

        if (!removed) {
            throw new IllegalArgumentException("El bloque horario no está asociado con esta agenda");
        }

        // Si el bloque no tiene más referencias, lo eliminamos
        bloqueHorarioRepository.delete(bloqueHorario);

        return agendaRepository.save(agenda);
    }

    @Transactional
    public Agenda updateBloquesHorariosDeAgenda(Long idAgenda, List<BloqueHorario> bloquesHorarioActualizados) {
        Agenda agenda = agendaRepository.findById(idAgenda)
                .orElseThrow(() -> new IllegalArgumentException(MSG_AGENDA + idAgenda));

        if (bloquesHorarioActualizados == null || bloquesHorarioActualizados.isEmpty()) {
            throw new IllegalArgumentException("La lista de bloques horarios está vacía");
        }

        // Obtener los bloques existentes en un solo query
        Map<Long, BloqueHorario> bloquesMap = bloqueHorarioRepository.findAllById(
                bloquesHorarioActualizados.stream().map(BloqueHorario::getIdBloque).toList()).stream()
                .collect(Collectors.toMap(BloqueHorario::getIdBloque, Function.identity()));

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
        bloqueHorarioRepository.saveAll(bloquesMap.values());

        return agendaRepository.save(agenda);
    }

}
