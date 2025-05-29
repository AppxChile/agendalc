package com.agendalc.agendalc.dto;

public class MovimientoSolicitudResponse {

    private Long idSolicitud;
    private String loginUsuario;
    private String tipoMovimiento;

    public MovimientoSolicitudResponse() {
    }

    public MovimientoSolicitudResponse(Long idSolicitud, String loginUsuario, String tipoMovimiento) {
        this.idSolicitud = idSolicitud;
        this.loginUsuario = loginUsuario;
        this.tipoMovimiento = tipoMovimiento;
    }

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

}
