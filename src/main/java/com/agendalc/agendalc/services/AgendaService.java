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

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AgendaService {

    private  static final String MSG_AGENDA="Agenda no encontrada con ID: ";
    private  static final String MSG_BLOQUE="Bloque horario no encontrado ";

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
    public Agenda crearAgenda(AgendaRequest request) {

        Tramite tramite = tramiteRepository.findById(request.getIdTramite())
                .orElseThrow(() -> new RuntimeException("Trámite no encontrado"));

        Agenda agenda = new Agenda();
        agenda.setTramite(tramite);
        agenda.setFecha(request.getFecha());

        if (request.getBloqueHorario() == null || request.getBloqueHorario().isEmpty()) {
            throw new IllegalArgumentException("La lista de bloques horarios está vacía o es nula");
        }

        Set<BloqueHorario> bloques = new HashSet<>();

        for (BloqueHorario bloqueRequest : request.getBloqueHorario()) {
            if (bloqueRequest.getIdBloque() == null) {
                BloqueHorario nuevoBloque = new BloqueHorario();
                nuevoBloque.setHoraInicio(bloqueRequest.getHoraInicio());
                nuevoBloque.setHoraFin(bloqueRequest.getHoraFin());
                nuevoBloque.setCuposDisponibles(bloqueRequest.getCuposDisponibles());

                bloqueRequest = bloqueHorarioRepository.save(nuevoBloque);
            } else {
                bloqueRequest = bloqueHorarioRepository.findById(bloqueRequest.getIdBloque())
                        .orElseThrow(() -> new RuntimeException(MSG_BLOQUE));
            }

            bloques.add(bloqueRequest);
        }

        agenda.setBloquesHorarios(bloques);

        return agendaRepository.save(agenda);
    }

    public List<Agenda> obtenerTodasLasAgendas() {
        return agendaRepository.findAll();
    }

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

    @Transactional
    public Agenda agregarBloquesAHorario(Long idAgenda, List<BloqueHorario> bloquesHorarios) {
        Agenda agenda = agendaRepository.findById(idAgenda)
                .orElseThrow(() -> new IllegalArgumentException(MSG_AGENDA+ idAgenda));

        if (bloquesHorarios == null || bloquesHorarios.isEmpty()) {
            throw new IllegalArgumentException("La lista de bloques horarios está vacía o es nula");
        }

        Set<BloqueHorario> bloquesExistentes = agenda.getBloquesHorarios();
        if (bloquesExistentes == null) {
            bloquesExistentes = new HashSet<>();
        }

        Set<BloqueHorario> nuevosBloques = new HashSet<>();

        for (BloqueHorario bloque : bloquesHorarios) {
            if (bloque.getIdBloque() == null) {
                BloqueHorario nuevoBloque = bloqueHorarioRepository.save(bloque);
                nuevosBloques.add(nuevoBloque);
            } else {
                BloqueHorario bloqueExistente = bloqueHorarioRepository.findById(bloque.getIdBloque())
                        .orElseThrow(() -> new IllegalArgumentException(
                            MSG_BLOQUE + bloque.getIdBloque()));
                nuevosBloques.add(bloqueExistente);
            }
        }

        bloquesExistentes.addAll(nuevosBloques);
        agenda.setBloquesHorarios(bloquesExistentes);

        return agendaRepository.save(agenda);
    }

    @Transactional
    public Agenda eliminarBloqueDeAgenda(Long idAgenda, Long idBloqueHorario) {
        Agenda agenda = agendaRepository.findById(idAgenda)
                .orElseThrow(() -> new IllegalArgumentException(MSG_AGENDA+ idAgenda));

        BloqueHorario bloqueHorario = bloqueHorarioRepository.findById(idBloqueHorario)
                .orElseThrow(
                        () -> new IllegalArgumentException(MSG_BLOQUE+ idBloqueHorario));

        if (!agenda.getBloquesHorarios().contains(bloqueHorario)) {
            throw new IllegalArgumentException("El bloque horario no está asociado con esta agenda");
        }

        agenda.getBloquesHorarios().remove(bloqueHorario);

        bloqueHorarioRepository.delete(bloqueHorario);

        return agendaRepository.save(agenda);
    }

    @Transactional
    public Agenda actualizarBloquesHorariosDeAgenda(Long idAgenda, List<BloqueHorario> bloquesHorarioActualizados) {
        Agenda agenda = agendaRepository.findById(idAgenda)
                .orElseThrow(() -> new IllegalArgumentException(MSG_AGENDA+ idAgenda));
    
        if (bloquesHorarioActualizados == null || bloquesHorarioActualizados.isEmpty()) {
            throw new IllegalArgumentException("La lista de bloques horarios está vacía");
        }
    
        for (BloqueHorario nuevoBloqueHorario : bloquesHorarioActualizados) {
            BloqueHorario bloqueHorario = bloqueHorarioRepository.findById(nuevoBloqueHorario.getIdBloque())
                    .orElseThrow(() -> new IllegalArgumentException(MSG_BLOQUE+ nuevoBloqueHorario.getIdBloque()));
    
            if (!agenda.getBloquesHorarios().contains(bloqueHorario)) {
                throw new IllegalArgumentException(MSG_BLOQUE + nuevoBloqueHorario.getIdBloque() + " no está asociado con esta agenda");
            }
    
            bloqueHorario.setHoraInicio(nuevoBloqueHorario.getHoraInicio());
            bloqueHorario.setHoraFin(nuevoBloqueHorario.getHoraFin());
            bloqueHorario.setCuposDisponibles(nuevoBloqueHorario.getCuposDisponibles());
    
            bloqueHorarioRepository.save(bloqueHorario);
        }
    
        return agenda; 
    }
    

}
