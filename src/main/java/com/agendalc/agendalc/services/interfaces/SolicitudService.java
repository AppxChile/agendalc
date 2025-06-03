package com.agendalc.agendalc.services.interfaces;

import java.io.IOException;
import java.util.List;

import com.agendalc.agendalc.dto.SolicitudRequest;
import com.agendalc.agendalc.dto.SolicitudResponse;
import com.agendalc.agendalc.dto.SolicitudResponseList;

public interface SolicitudService {

    List<SolicitudResponseList> getSolicitudes(int year);

    void assignOrDerivateSolicitud(Long idSolicitud, String loginUsuario, String derivadoA, int tipo);

    void finishSolicitudById(Long idSolicitud, String loginUsuario);

    List<SolicitudResponseList> getSolicitudesByRut(Integer rut);

    SolicitudResponse createSolicitud(SolicitudRequest request) throws IOException;

    List<SolicitudResponseList> getSolicitudesByRutFunc(Integer rut);

}
