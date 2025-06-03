package com.agendalc.agendalc.dto;

import java.util.List; // Importa List

public class SolicitudRequest {

    private Long idTramite;
    private Integer rut;
    private List<DocumentosSubidosRequest> documentos;

    public SolicitudRequest() {
    }

    public SolicitudRequest(Long idTramite, Integer rut, List<DocumentosSubidosRequest> documentos) {
        this.idTramite = idTramite;
        this.rut = rut;
        this.documentos = documentos;
    }

    // Getters y Setters
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

    public List<DocumentosSubidosRequest> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentosSubidosRequest> documentos) {
        this.documentos = documentos;
    }
}