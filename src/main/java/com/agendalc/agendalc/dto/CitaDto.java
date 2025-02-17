package com.agendalc.agendalc.dto;

import java.time.LocalDateTime;

import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.entities.Cita.EstadoCita;

public class CitaDto {
    private Long idCita;
    private Integer rut;
    private Long idAgenda;
    private EstadoCita estado;
    private LocalDateTime fechaHora;

    public CitaDto(Cita cita) {
        this.idCita = cita.getIdCita();
        this.rut = cita.getRut();
        this.idAgenda = cita.getAgenda().getIdAgenda();
        this.estado = cita.getEstado();
        this.fechaHora = cita.getFechaHora();
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

    public Long getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(Long idAgenda) {
        this.idAgenda = idAgenda;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

}
