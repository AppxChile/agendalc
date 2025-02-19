package com.agendalc.agendalc.dto;

import java.util.List;

import com.agendalc.agendalc.entities.BloqueHorario;

public class AgendaBloquesRequest {
    private List<BloqueHorario> bloquesHorarios;

    public List<BloqueHorario> getBloquesHorarios() {
        return bloquesHorarios;
    }

    public void setBloquesHorarios(List<BloqueHorario> bloquesHorarios) {
        this.bloquesHorarios = bloquesHorarios;
    }
}