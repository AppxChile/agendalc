package com.agendalc.agendalc.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgenda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tramite", nullable = false)
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    private Tramite tramite;

    @Column(nullable = false)
    private LocalDate fecha;

    @ManyToMany
    @JoinTable(name = "agenda_bloque_horario", joinColumns = @JoinColumn(name = "id_agenda"), inverseJoinColumns = @JoinColumn(name = "id_bloque"))
    private Set<BloqueHorario> bloquesHorarios = new HashSet<>();

    @OneToMany(mappedBy = "agenda", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Cita> citas = new HashSet<>();

    public Long getIdAgenda() {
        return idAgenda;
    }

    public void setIdAgenda(Long idAgenda) {
        this.idAgenda = idAgenda;
    }

    public Set<BloqueHorario> getBloquesHorarios() {
        return bloquesHorarios;
    }

    public void setBloquesHorarios(Set<BloqueHorario> bloquesHorarios) {
        this.bloquesHorarios = bloquesHorarios;
    }

    public void agregarBloqueHorario(BloqueHorario bloque) {
        this.bloquesHorarios.add(bloque);
    }

    public void removerBloqueHorario(BloqueHorario bloque) {
        this.bloquesHorarios.remove(bloque);
    }

    public Tramite getTramite() {
        return tramite;
    }

    public void setTramite(Tramite tramite) {
        this.tramite = tramite;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Set<Cita> getCitas() {
        return citas;
    }

    public void setCitas(Set<Cita> citas) {
        this.citas = citas;
    }

    public Long getIdTramite() {
        return (tramite != null) ? tramite.getIdTramite() : null;
    }

    public String getNombreTramite() {
        return (tramite != null) ? tramite.getNombre() : null;
    }
}
