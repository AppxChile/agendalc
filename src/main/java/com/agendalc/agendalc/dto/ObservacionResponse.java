package com.agendalc.agendalc.dto;

public class ObservacionResponse {

    private Long idObservacion;
    private Long idSolicitud;
    private String glosa;

    public ObservacionResponse(Long idObservacion, Long idSolicitud, String glosa) {
        this.idObservacion = idObservacion;
        this.idSolicitud = idSolicitud;
        this.glosa = glosa;
    }

    public Long getIdObservacion() {
        return idObservacion;
    }

    public void setIdObservacion(Long idObservacion) {
        this.idObservacion = idObservacion;
    }

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

}
