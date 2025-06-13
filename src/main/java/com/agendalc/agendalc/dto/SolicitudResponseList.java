package com.agendalc.agendalc.dto;

import java.time.LocalDate;
import java.util.Set;

public class SolicitudResponseList {

    private Long idSolicitud;
    private LocalDate fechaSolicitud;
    private Integer rut;
    private String vrut;
    private String nonbre;
    private LocalDate fechaFinalizacion;
    private String estadoSolicitud;
    private String nombreTramite;
    private Long idTramite;
    private Set<MovimientosDto> movimientos;
    private Set<ObservacionesDto> observaciones;
    private Set<DocumentosDto> documentos;

    public String getNombreTramite() {
        return nombreTramite;
    }

    public void setNombreTramite(String nombreTramite) {
        this.nombreTramite = nombreTramite;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

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

    public LocalDate getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(LocalDate fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    public Set<MovimientosDto> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(Set<MovimientosDto> movimientos) {
        this.movimientos = movimientos;
    }

    public Set<ObservacionesDto> getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(Set<ObservacionesDto> observaciones) {
        this.observaciones = observaciones;
    }

    public Set<DocumentosDto> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(Set<DocumentosDto> documentos) {
        this.documentos = documentos;
    }

}
