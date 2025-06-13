package com.agendalc.agendalc.services.interfaces;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.agendalc.agendalc.dto.SolicitudRequest;
import com.agendalc.agendalc.dto.SolicitudResponse;
import com.agendalc.agendalc.dto.SolicitudResponseList;
import com.agendalc.agendalc.entities.Solicitud.EstadoSolicitud;

public interface SolicitudService {

    List<SolicitudResponseList> getSolicitudes(int year);

    void assignOrDerivateSolicitud(Long idSolicitud, String loginUsuario, String derivadoA, int tipo);

    void finishSolicitudById(Long idSolicitud, String loginUsuario);

    List<SolicitudResponseList> getSolicitudesByRut(Integer rut);

    SolicitudResponse createSolicitud(SolicitudRequest request) throws IOException;

    List<SolicitudResponseList> getSolicitudesByRutFunc(Integer rut);

    List<SolicitudResponseList> getSolicitudesBetweenDatesAndState(LocalDate fechaInicio, LocalDate fechaFin,
            EstadoSolicitud estadoSolicitud);

    void replaceFile(Long idSolicitud, Long idTipo, MultipartFile file, String loginUsuario) throws IOException;

    SolicitudResponse aprobeSolicitud(Long idSolicitud, String loginUsuario);

    SolicitudResponse rejectSolicitu(Long idSolicitud, String loginUsuario);

    void delteSolicitud(Long idSolicitud);

}
