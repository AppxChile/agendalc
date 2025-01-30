package com.agendalc.agendalc.controllers.dto;

public class CitaRequest {

    private Long idAgenda;
    private Integer rut;
    private String estado;
    public Integer getRut() {
        return rut;
    }
    public void setRut(Integer rut) {
        this.rut = rut;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public Long getIdAgenda() {
        return idAgenda;
    }
    public void setIdAgenda(Long idAdenga) {
        this.idAgenda = idAdenga;
    }

    

    

}
