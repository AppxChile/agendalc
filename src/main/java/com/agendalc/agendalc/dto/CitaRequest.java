package com.agendalc.agendalc.dto;

public class CitaRequest {

    private Long idAgenda;
    private Integer rut;
    private String estado;
    private Long idBloqueHorario;


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
    public Long getIdBloqueHorario() {
        return idBloqueHorario;
    }
    public void setIdBloqueHorario(Long getIdBloqueHorario) {
        this.idBloqueHorario = getIdBloqueHorario;
    }

    

    

}
