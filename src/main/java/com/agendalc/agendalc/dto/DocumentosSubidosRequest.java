package com.agendalc.agendalc.dto;

import org.springframework.web.multipart.MultipartFile;

public class DocumentosSubidosRequest {
    private Long idTipoDocumento;
    private MultipartFile file;

    public DocumentosSubidosRequest() {
    }

    public DocumentosSubidosRequest(Long idTipoDocumento, MultipartFile file) {
        this.idTipoDocumento = idTipoDocumento;
        this.file = file;
    }

    public Long getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(Long idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}