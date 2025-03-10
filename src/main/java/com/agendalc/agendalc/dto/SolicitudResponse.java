package com.agendalc.agendalc.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class SolicitudResponse {

    private Long idSolicitud;
    private String asignadoA;
    private LocalDate fechaSolicitud;
    private Integer rut;
    private String vrut;
    private String nonbre;


    private LocalDateTime fechaHoraCita;
    private String estadoSolicitud;


    public String getNonbre() {
        return nonbre;
    }

    public void setNonbre(String nonbre) {
        this.nonbre = nonbre;
    }


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

    public LocalDateTime getFechaHoraCita() {
        return fechaHoraCita;
    }

    public void setFechaHoraCita(LocalDateTime fechaHora) {
        this.fechaHoraCita = fechaHora;
    }

    public String getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(String estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }



    public String getVrut() {
        return vrut;
    }

    public void setVrut(String vrut) {
        this.vrut = vrut;
    }

}
