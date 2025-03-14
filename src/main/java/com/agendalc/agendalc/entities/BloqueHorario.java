package com.agendalc.agendalc.entities;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class BloqueHorario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBloque;

    @Column(nullable = false)
    private LocalTime horaInicio;

    @Column(nullable = false)
    private LocalTime horaFin;

    @Column(nullable = false)
    private int cuposDisponibles;

    @ManyToMany(mappedBy = "bloquesHorarios")
    @JsonIgnore
    private Set<Agenda> agendas = new HashSet<>();

    public BloqueHorario() {
    }

    public BloqueHorario(LocalTime horaInicio, LocalTime horaFin, int cuposDisponibles) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.cuposDisponibles = cuposDisponibles;
    }

    public Long getIdBloque() {
        return idBloque;
    }

    public void setIdBloque(Long idBloque) {
        this.idBloque = idBloque;
    }

    public Set<Agenda> getAgendas() {
        return agendas;
    }

    public void setAgendas(Set<Agenda> agendas) {
        this.agendas = agendas;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public int getCuposDisponibles() {
        return cuposDisponibles;
    }

    public void setCuposDisponibles(int cuposDisponibles) {
        this.cuposDisponibles = cuposDisponibles;
    }
}
