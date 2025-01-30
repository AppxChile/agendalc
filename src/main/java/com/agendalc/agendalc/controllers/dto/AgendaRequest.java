package com.agendalc.agendalc.controllers.dto;

import java.time.LocalDate;

public class AgendaRequest {

    private Long idTramite;
    private Long idBloque;
    private LocalDate fecha;
    private int cuposDisponibles;
    
    public Long getIdTramite() {
        return idTramite;
    }
    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }
    public Long getIdBloque() {
        return idBloque;
    }
    public void setIdBloque(Long idBloque) {
        this.idBloque = idBloque;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public int getCuposDisponibles() {
        return cuposDisponibles;
    }
    public void setCuposDisponibles(int cuposDisponibles) {
        this.cuposDisponibles = cuposDisponibles;
    }

    


}
