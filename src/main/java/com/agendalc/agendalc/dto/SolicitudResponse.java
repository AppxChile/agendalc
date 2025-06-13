package com.agendalc.agendalc.dto;

public class SolicitudResponse {

    private Long idSolicitud;
    private String nombreTramites;
    private Long idTramite;
    private Integer rut;
    private String estado;

    

    public SolicitudResponse() {
    }

    public SolicitudResponse(Long idSolicitud, String nombreTramites, Long idTramite, Integer rut, String estado) {
        this.idSolicitud = idSolicitud;
        this.nombreTramites = nombreTramites;
        this.idTramite = idTramite;
        this.rut = rut;
        this.estado = estado;
    }

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getNombreTramites() {
        return nombreTramites;
    }

    public void setNombreTramites(String nombreTramites) {
        this.nombreTramites = nombreTramites;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
