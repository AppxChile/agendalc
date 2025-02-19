package com.agendalc.agendalc.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SolicitudResponse {

    private Long idSolicitud;
    private String asignadoA;
    private LocalDate fechaSolicitud;
    private Integer rut;
    private LocalDateTime fechaHora;
    private String estadoSolicitud;
    private String estadoCita;
    public Long getIdSolicitud() {
        return idSolicitud;
    }
    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }
    public String getAsignadoA() {
        return asignadoA;
    }
    public void setAsignadoA(String asignadoA) {
        this.asignadoA = asignadoA;
    }
    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }
    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
    public Integer getRut() {
        return rut;
    }
    public void setRut(Integer rut) {
        this.rut = rut;
    }
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }
    public String getEstadoSolicitud() {
        return estadoSolicitud;
    }
    public void setEstadoSolicitud(String estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }
    public String getEstadoCita() {
        return estadoCita;
    }
    public void setEstadoCita(String estadoCita) {
        this.estadoCita = estadoCita;
    }
    
    


}
