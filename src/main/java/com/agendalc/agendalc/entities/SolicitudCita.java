package com.agendalc.agendalc.entities;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
public class SolicitudCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSolicitud;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cita", nullable = false, unique = true)
    private Cita cita;

    @Column(nullable = true) 
    private String asignadoA;

    @Column(nullable = false, updatable = false)
    private LocalDate fechaSolicitud;

    
    private LocalDate fechaFinalizacion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoSolicitud estado;

    public enum EstadoSolicitud {
        PENDIENTE, 
        ASIGNADA,  
        FINALIZADA 
    }

    @PrePersist
    protected void onCreate() {
        this.fechaSolicitud = LocalDate.now();
        this.estado = EstadoSolicitud.PENDIENTE;
    }

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
        this.estado = EstadoSolicitud.ASIGNADA; 
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    public void setEstado(EstadoSolicitud estado) {
        this.estado = estado;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public LocalDate getFechaFinalizacion() {
        return fechaFinalizacion;
    }

    public void setFechaFinalizacion(LocalDate fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
    }

    
}
