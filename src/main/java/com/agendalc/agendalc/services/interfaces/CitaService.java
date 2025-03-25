package com.agendalc.agendalc.services.interfaces;

import java.util.List;

import com.agendalc.agendalc.dto.CitaDto;
import com.agendalc.agendalc.dto.CitaRequest;
import com.agendalc.agendalc.dto.SolicitudCitaResponse;
import com.agendalc.agendalc.entities.Cita;

public interface CitaService {

    CitaDto createCita(CitaRequest citaRequest);

    Cita findById(Long id);

    List<SolicitudCitaResponse> getCitaByRut(Integer rut);

    Cita updateCita(Long id, Cita citaActualizada);

    boolean deleteCitaById(Long id);

}
