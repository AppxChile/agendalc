package com.agendalc.agendalc.dto;

import java.time.LocalDate;
import java.util.Set;
import com.agendalc.agendalc.entities.BloqueHorario;

public class AgendaRequest {

    private Long idTramite;
    private Set<BloqueHorario> bloqueHorario;
    private LocalDate fecha;

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Set<BloqueHorario> getBloqueHorario() {
        return bloqueHorario;
    }

    public void setBloqueHorario(Set<BloqueHorario> bloqueHorario) {
        this.bloqueHorario = bloqueHorario;
    }
}
