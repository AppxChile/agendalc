package com.agendalc.agendalc.services.interfaces;

import java.util.List;

import com.agendalc.agendalc.dto.SolicitudCitaResponse;
import com.agendalc.agendalc.dto.SolicitudRequest;
import com.agendalc.agendalc.dto.SolicitudResponse;
import com.agendalc.agendalc.dto.SolicitudResponseList;

public interface SolicitudService {

    List<SolicitudResponseList> getSolicitudes();

    List<SolicitudResponseList> getSolicitudesPendientes();

    void assignSolicitud(Long idSolicitud, String loginUsuario);

    void finishSolicitudById(Long idSolicitud);



    List<SolicitudCitaResponse> getSolicituCitasByRut(Integer rut) ;

    SolicitudResponse createSolicitud(SolicitudRequest request);

}
