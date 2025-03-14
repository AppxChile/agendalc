package com.agendalc.agendalc.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class SolicitudCitaResponse {

    private Integer rut;
    private LocalDate fechaSolicitud;
    private LocalDate fechaAgenda;
    private Long idBloque;
    private LocalTime horaInicioBloque;
    private LocalTime horaFinBloque;
    private String nombre;
    private String vrut;

    public LocalDate getFechaAgenda() {
        return fechaAgenda;
    }

    public void setFechaAgenda(LocalDate fechaAgenda) {
        this.fechaAgenda = fechaAgenda;
    }

    public Long getIdBloque() {
        return idBloque;
    }

    public void setIdBloque(Long idBloque) {
        this.idBloque = idBloque;
    }

    public LocalTime getHoraInicioBloque() {
        return horaInicioBloque;
    }

    public void setHoraInicioBloque(LocalTime fechaInicioBloque) {
        this.horaInicioBloque = fechaInicioBloque;
    }

    public LocalTime getHoraFinBloque() {
        return horaFinBloque;
    }

    public void setHoraFinBloque(LocalTime fechaFinBloque) {
        this.horaFinBloque = fechaFinBloque;
    }

    private String estado;

    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fecha) {
        this.fechaSolicitud = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVrut() {
        return vrut;
    }

    public void setVrut(String vrut) {
        this.vrut = vrut;
    }

}
