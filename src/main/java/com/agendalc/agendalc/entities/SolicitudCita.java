package com.agendalc.agendalc.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
public class SolicitudCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSolicitud;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cita", nullable = false, unique = true)
    private Cita cita;

    @Column(nullable = true) // Puede ser null si no está asignada aún
    private String asignadoA; // Guarda el login del usuario asignado

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaSolicitud;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSolicitud estado;

    public enum EstadoSolicitud {
        PENDIENTE, // Aún no asignada
        ASIGNADA,  // Tiene usuario asignado
        FINALIZADA // Cita completada
    }

    @PrePersist
    protected void onCreate() {
        this.fechaSolicitud = LocalDateTime.now();
        this.estado = EstadoSolicitud.PENDIENTE;
    }

    // Getters y Setters
    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public String getAsignadoA() {
        return asignadoA;
    }

    public void setAsignadoA(String asignadoA) {
        this.asignadoA = asignadoA;
        this.estado = EstadoSolicitud.ASIGNADA; // Cambia el estado al asignar
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    
}
