package com.agendalc.agendalc.dto;

public class TramiteRequest {

    private String nombre;
    private String descripcion;
    private boolean requiereSolicitud;
    private boolean pideDocumentos;

    public boolean isRequiereSolicitud() {
        return requiereSolicitud;
    }

    public void setRequiereSolicitud(boolean requiereSolicitud) {
        this.requiereSolicitud = requiereSolicitud;
    }

    public boolean isPideDocumentos() {
        return pideDocumentos;
    }

    public void setPideDocumentos(boolean pideDocumentos) {
        this.pideDocumentos = pideDocumentos;
    }

    public TramiteRequest() {
    }

    public TramiteRequest(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
