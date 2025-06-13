package com.agendalc.agendalc.dto;

public class BloqueHorarioResponse {

    private Long id;
    private String horaInicio;
    private String horaFin;
    private Integer cuposDisponibles;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getHoraInicio() {
        return horaInicio;
    }
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }
    public String getHoraFin() {
        return horaFin;
    }
    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }
    public Integer getCuposDisponibles() {
        return cuposDisponibles;
    }
    public void setCuposDisponibles(Integer cuposDisponibles) {
        this.cuposDisponibles = cuposDisponibles;
    }

    

}
