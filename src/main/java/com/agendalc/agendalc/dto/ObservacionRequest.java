package com.agendalc.agendalc.dto;

public class ObservacionRequest {

    private Long idSolicitud;
    private String observacion;
    private String loginUsuario;

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitus) {
        this.idSolicitud = idSolicitus;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

}
