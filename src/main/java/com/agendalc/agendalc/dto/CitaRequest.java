package com.agendalc.agendalc.dto;

public class CitaRequest {

    private Long idAgenda;
    private Integer rut;
    private Long idBloqueHorario;

    public Integer getRut() {
        return rut;
    }

    public void setRut(Integer rut) {
        this.rut = rut;
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
