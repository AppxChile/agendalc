package com.agendalc.agendalc.entities;


import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ObservacionSolicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idObservacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitud", nullable = false)
    private Solicitud solicitud;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String glosa; // El contenido de la observación

    @Column(nullable = false)
    private LocalDateTime fechaObservacion;

    @Column(nullable = false)
    private String usuarioResponsable; // Quién realizó la observación

    @PrePersist
    protected void onCreate() {
        this.fechaObservacion = LocalDateTime.now();
    }

    // Constructor
    public ObservacionSolicitud() {}

    public ObservacionSolicitud(Solicitud solicitudCita, String glosa, String usuarioResponsable) {
        this.solicitud = solicitudCita;
        this.glosa = glosa;
        this.usuarioResponsable = usuarioResponsable;
    }

    // Getters y Setters
    public Long getIdObservacion() {
        return idObservacion;
    }

    public void setIdObservacion(Long idObservacion) {
        this.idObservacion = idObservacion;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitudCita) {
        this.solicitud = solicitudCita;
    }

    public String getGlosa() {
        return glosa;
    }

    public void setGlosa(String glosa) {
        this.glosa = glosa;
    }

    public LocalDateTime getFechaObservacion() {
        return fechaObservacion;
    }

    public void setFechaObservacion(LocalDateTime fechaObservacion) {
        this.fechaObservacion = fechaObservacion;
    }

    public String getUsuarioResponsable() {
        return usuarioResponsable;
    }

    public void setUsuarioResponsable(String usuarioResponsable) {
        this.usuarioResponsable = usuarioResponsable;
    }
}