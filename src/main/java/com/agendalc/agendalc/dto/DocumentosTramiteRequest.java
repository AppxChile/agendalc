package com.agendalc.agendalc.dto;

public class DocumentosTramiteRequest {

    private Long idTramite;
    private String nombreDocumento;

    public DocumentosTramiteRequest() {
    }

    public DocumentosTramiteRequest(Long idTramite, String nombreDocumento) {
        this.idTramite = idTramite;
        this.nombreDocumento = nombreDocumento;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

}
