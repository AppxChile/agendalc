package com.agendalc.agendalc.dto;

import java.util.List;



public class TramiteResponse {

    private Long idTramite;
    private String nombreTramite;
    private String descripcionTramite;
    private boolean pideDocumentos;
    private boolean requiereSolicitud;
    private List<DocumentosTramiteResponse> documentosRequeridos;

    public boolean isPideDocumentos() {
        return pideDocumentos;
    }

    public void setPideDocumentos(boolean pideDocumentos) {
        this.pideDocumentos = pideDocumentos;
    }

    public boolean isRequiereSolicitud() {
        return requiereSolicitud;
    }

    public void setRequiereSolicitud(boolean requiereSolicitud) {
        this.requiereSolicitud = requiereSolicitud;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public String getNombreTramite() {
        return nombreTramite;
    }

    public void setNombreTramite(String nombreTramite) {
        this.nombreTramite = nombreTramite;
    }

    public String getDescripcionTramite() {
        return descripcionTramite;
    }

    public void setDescripcionTramite(String descripcionTramite) {
        this.descripcionTramite = descripcionTramite;
    }

    public List<DocumentosTramiteResponse> getDocumentosRequeridos() {
        return documentosRequeridos;
    }

    public void setDocumentosRequeridos(List<DocumentosTramiteResponse> documentosRequeridos) {
        this.documentosRequeridos = documentosRequeridos;
    }

}
