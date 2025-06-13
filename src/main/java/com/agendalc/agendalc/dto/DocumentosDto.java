package com.agendalc.agendalc.dto;

public class DocumentosDto {

    private Long idDocumento;
    private String nombreDocumento;
    private String tipoDocumento;
    private boolean aprobado;

    public DocumentosDto() {
    }

    public DocumentosDto(Long idDocumento, String nombreDocumento, String tipoDocumento, boolean aprobado) {
        this.idDocumento = idDocumento;
        this.nombreDocumento = nombreDocumento;
        this.tipoDocumento = tipoDocumento;
        this.aprobado = aprobado;
    }

    public Long getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
    }

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

}
