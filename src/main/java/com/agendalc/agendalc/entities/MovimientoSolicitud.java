package com.agendalc.agendalc.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MovimientoSolicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_solicitud", nullable = false)
    private Solicitud solicitud;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMovimiento tipo;

    @Column(nullable = false)
    private LocalDateTime fechaMovimiento;

    @Column(nullable = true)
    private String usuarioResponsable; // Quién realizó el movimiento

    @Column(nullable = true)
    private String asignadoA; // Si el movimiento es una asignación/derivación

    public enum TipoMovimiento {
        CREACION,
        ASIGNACION,
        DERIVACION,
        APROBACION,
        RECHAZO,
        OBSERVACION_AGREGADA, // Un movimiento genérico para cuando se añade una observación
        FINALIZACION,
        OBSERVACION_RESPONDIDA
        // Otros tipos que consideres necesarios
    }

    @PrePersist
    protected void onCreate() {
        this.fechaMovimiento = LocalDateTime.now();
    }

    // Constructor
    public MovimientoSolicitud() {
    }

    public MovimientoSolicitud(Solicitud solicitudCita, TipoMovimiento tipo, String usuarioResponsable,
            String asignadoA) {
        this.solicitud = solicitudCita;
        this.tipo = tipo;
        this.usuarioResponsable = usuarioResponsable;
        this.asignadoA = asignadoA;
    }

    // Getters y Setters
    public Long getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(Long idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitudCita) {
        this.solicitud = solicitudCita;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimiento tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getFechaMovimiento() {
        return fechaMovimiento;
    }

    public void setFechaMovimiento(LocalDateTime fechaMovimiento) {
        this.fechaMovimiento = fechaMovimiento;
    }

    public String getUsuarioResponsable() {
        return usuarioResponsable;
    }

    public void setUsuarioResponsable(String usuarioResponsable) {
        this.usuarioResponsable = usuarioResponsable;
    }

    public String getAsignadoA() {
        return asignadoA;
    }

    public void setAsignadoA(String asignadoA) {
        this.asignadoA = asignadoA;
    }

    public String nombreTipo() {
        return tipo.name();
    }
}