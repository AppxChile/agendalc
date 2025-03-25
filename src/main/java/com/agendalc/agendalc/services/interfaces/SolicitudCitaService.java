package com.agendalc.agendalc.services.interfaces;

import java.util.List;

import com.agendalc.agendalc.dto.SolicitudCitaResponse;
import com.agendalc.agendalc.dto.SolicitudResponse;
import com.agendalc.agendalc.entities.SolicitudCita;

public interface SolicitudCitaService {

    List<SolicitudResponse> getSolicitudes();

    List<SolicitudResponse> getSolicitudesPendientes();

    void assignSolicitud(Long idSolicitud, String loginUsuario);

    void finishSolicitudById(Long idSolicitud);

    List<SolicitudResponse> getSolicitudesUnassigned();

    List<SolicitudResponse> getSolicitudesAssignByUser(String username);

    List<SolicitudCitaResponse> getSolicituCitasByRut(Integer rut) ;

    SolicitudCita save(SolicitudCita solicitudCita);

}
