package com.agendalc.agendalc.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class ObservacionSolicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idObservacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitud", nullable = false)
    private Solicitud solicitud;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String glosa;

    @Column(nullable = false)
    private LocalDate fechaObservacion;

    @Column(nullable = false)
    private String usuarioResponsable;

    private boolean revisada;

    @PrePersist
    protected void onCreate() {
        this.fechaObservacion = LocalDate.now();
        this.revisada = false;
    }

    public ObservacionSolicitud() {
    }

    public ObservacionSolicitud(Solicitud solicitud, String glosa, String usuarioResponsable) {
        this.solicitud = solicitud;
        this.glosa = glosa;
        this.usuarioResponsable = usuarioResponsable;
    }

    public Long getIdObservacion() {
        return idObservacion;
    }

    public void setIdObservacion(Long idObservacion) {
        this.idObservacion = idObservacion;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public LocalDate getFechaObservacion() {
        return fechaObservacion;
    }

    public void setFechaObservacion(LocalDate fechaObservacion) {
        this.fechaObservacion = fechaObservacion;
    }

    public String getUsuarioResponsable() {
        return usuarioResponsable;
    }

    public void setUsuarioResponsable(String usuarioResponsable) {
        this.usuarioResponsable = usuarioResponsable;
    }

    public boolean isRevisada() {
        return revisada;
    }

    public void setRevisada(boolean revisada) {
        this.revisada = revisada;
    }
}