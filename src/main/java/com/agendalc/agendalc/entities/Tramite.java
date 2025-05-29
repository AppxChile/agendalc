package com.agendalc.agendalc.entities;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Tramite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTramite;

    @Column(nullable = false)
    private String nombre;

    private String descripcion;

    @OneToMany(mappedBy = "tramite")
    @JsonIgnore
    private Set<Agenda> agendas;

    @OneToMany(mappedBy = "tramite", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DocumentosTramite> documentosRequeridos;

    private boolean pideDocumentos;

    private boolean requiereSolicitud;

    public boolean isPideDocumentos() {
        return pideDocumentos;
    }

    public void setPideDocumentos(boolean pideDocumentos) {
        this.pideDocumentos = pideDocumentos;
    }

    public boolean isRequiereSolicitud() {
        return requiereSolicitud;
    }

    public void setRequiereSolicitud(boolean requiereSolicitud) {
        this.requiereSolicitud = requiereSolicitud;
    }

    public Long getIdTramite() {
        return idTramite;
    }

    public void setIdTramite(Long idTramite) {
        this.idTramite = idTramite;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Agenda> getAgendas() {
        return agendas;
    }

    public void setAgendas(Set<Agenda> agendas) {
        this.agendas = agendas;
    }

    public List<DocumentosTramite> getDocumentosRequeridos() {
        return documentosRequeridos;
    }

    public void setDocumentosRequeridos(List<DocumentosTramite> documentosRequeridos) {
        this.documentosRequeridos = documentosRequeridos;
    }

}
