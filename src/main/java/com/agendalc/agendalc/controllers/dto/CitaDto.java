package com.agendalc.agendalc.controllers.dto;

import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.entities.Cita.EstadoCita;

public class CitaDto {

    private Long idCita;
    private Integer rut;
    private EstadoCita estado;
    private Long idAgenda; // Solo el ID, no el objeto completo

    public CitaDto(Cita cita) {
        this.idCita = cita.getIdCita();
        this.rut = cita.getRut();
        this.estado = cita.getEstado();
        this.idAgenda = cita.getAgenda().getIdAgenda();
    }

    public Long getIdCita() {
        return idCita;
    }

    public void setIdCita(Long idCita) {
        this.idCita = idCita;
    }

    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public Long getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(Long idAgenda) {
        this.idAgenda = idAgenda;
    }


    

}
