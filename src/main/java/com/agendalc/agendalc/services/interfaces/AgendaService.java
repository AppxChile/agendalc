package com.agendalc.agendalc.services.interfaces;

import java.util.List;

import com.agendalc.agendalc.dto.AgendaRequest;
import com.agendalc.agendalc.dto.AgendaResponse;
import com.agendalc.agendalc.entities.Agenda;
import com.agendalc.agendalc.entities.BloqueHorario;

public interface AgendaService {

    Agenda createAgenda(AgendaRequest request);

    List<AgendaResponse> getAllAgendas();

    Agenda updateAgenda(Long id, Agenda agendaActualizada);

    boolean deleteAgendaById(Long id);

    Agenda addOrUpdateBloquesHorario(Long idAgenda, List<BloqueHorario> bloquesHorarios);

    Agenda deleteBloqueDeAgenda(Long idAgenda, Long idBloqueHorario);

    Agenda updateBloquesHorariosDeAgenda(Long idAgenda, List<BloqueHorario> bloquesHorarioActualizados);

    Agenda findById(Long id);

    Agenda save(Agenda agenda);
}
