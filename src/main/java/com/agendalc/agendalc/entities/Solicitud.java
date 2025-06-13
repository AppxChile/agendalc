package com.agendalc.agendalc.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;

@Entity
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSolicitud;

    @Column(nullable = false)
    private Integer rut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tramite", nullable = false)
    private Tramite tramite;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSolicitud estado;

    @Column(nullable = false, updatable = false)
    private LocalDate fechaSolicitud;

    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovimientoSolicitud> movimientos = new HashSet<>();

    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ObservacionSolicitud> observaciones = new HashSet<>();

    @OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentosSolicitud> documentosEntregados;

    private String asignadoA;

    public enum EstadoSolicitud {
        PENDIENTE,
        ASIGNADA,
        EN_PROCESO,
        APROBADA,
        RECHAZADA,
        DERIVADA,
        FINALIZADA,
        OBSERVADA,
        RESPONDIDA
    }

    @PrePersist
    protected void onCreate() {
        this.fechaSolicitud = LocalDate.now();
        this.estado = EstadoSolicitud.PENDIENTE; // Estado inicial
    }

    // Getters y Setters
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public Set<MovimientoSolicitud> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(Set<MovimientoSolicitud> movimientos) {
        this.movimientos = movimientos;
    }

    public void addMovimiento(MovimientoSolicitud movimiento) {
        this.movimientos.add(movimiento);
        movimiento.setSolicitud(this);
    }

    public Set<ObservacionSolicitud> getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(Set<ObservacionSolicitud> observaciones) {
        this.observaciones = observaciones;
    }

    public void addObservacion(ObservacionSolicitud observacion) {
        this.observaciones.add(observacion);
        observacion.setSolicitud(this);
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public List<DocumentosSolicitud> getDocumentosEntregados() {
        return documentosEntregados;
    }

    public void setDocumentosEntregados(List<DocumentosSolicitud> documentosEntregados) {
        this.documentosEntregados = documentosEntregados;
    }

    public String getAsignadoA() {
        return asignadoA;
    }

    public void setAsignadoA(String asignadoA) {
        this.asignadoA = asignadoA;
    }

    public Long getIdTramite() {
        if (this.tramite != null) {
            return this.tramite.getIdTramite();
        }
        return null;
    }

    public String getNombreTramite() {
        if (this.tramite != null) {
            return this.tramite.getNombre();
        }
        return null;
    }

}