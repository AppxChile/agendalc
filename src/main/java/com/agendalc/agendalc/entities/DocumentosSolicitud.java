package com.agendalc.agendalc.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class DocumentosSolicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDocumentoSolicitud;

    @ManyToOne
    @JoinColumn(name = "solicitud_id", nullable = false)
    private Solicitud solicitud;

    @ManyToOne
    @JoinColumn(name = "documento_id", nullable = false)
    private DocumentosTramite documentosTramite;

    private String rutaDocumento;

    private boolean aprobado;

    public DocumentosSolicitud() {
    }

    public DocumentosSolicitud(Solicitud solicitud, DocumentosTramite documentosTramite, String rutaDocumento) {
        this.solicitud = solicitud;
        this.documentosTramite = documentosTramite;
        this.rutaDocumento = rutaDocumento;
    }

    public Long getIdDocumentoSolicitud() {
        return idDocumentoSolicitud;
    }

    public void setIdDocumentoSolicitud(Long idDocumentoSolicitud) {
        this.idDocumentoSolicitud = idDocumentoSolicitud;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public DocumentosTramite getDocumentosTramite() {
        return documentosTramite;
    }

    public void setDocumentosTramite(DocumentosTramite documentosTramite) {
        this.documentosTramite = documentosTramite;
    }

    public String getRutaDocumento() {
        return rutaDocumento;
    }

    public void setRutaDocumento(String rutaDocumento) {
        this.rutaDocumento = rutaDocumento;
    }

    public boolean isAprobado() {
        return aprobado;
    }

    public void setAprobado(boolean aprobado) {
        this.aprobado = aprobado;
    }

    public String getNombreDocumento() {
        return documentosTramite.getNombreDocumento();
    }

}
