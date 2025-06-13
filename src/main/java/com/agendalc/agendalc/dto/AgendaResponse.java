package com.agendalc.agendalc.dto;

import java.time.LocalDate;
import java.util.List;

public class AgendaResponse {

    private Long idAgenda;
    private LocalDate fechaAgenda;
    private TramiteResponse tramite;
    private List<BloqueHorarioResponse> bloqueHorario;
    public Long getIdAgenda() {
        return idAgenda;
    }
    public void setIdAgenda(Long idAgenda) {
        this.idAgenda = idAgenda;
    }
    public LocalDate getFechaAgenda() {
        return fechaAgenda;
    }
    public void setFechaAgenda(LocalDate fechaAgeda) {
        this.fechaAgenda = fechaAgeda;
    }
    public TramiteResponse getTramite() {
        return tramite;
    }
    public void setTramite(TramiteResponse tramite) {
        this.tramite = tramite;
    }
    public List<BloqueHorarioResponse> getBloqueHorario() {
        return bloqueHorario;
    }
    public void setBloqueHorario(List<BloqueHorarioResponse> bloqueHorario) {
        this.bloqueHorario = bloqueHorario;
    }

    






}
