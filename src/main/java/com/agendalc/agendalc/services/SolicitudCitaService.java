package com.agendalc.agendalc.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.dto.SolicitudResponse;
import com.agendalc.agendalc.entities.SolicitudCita;
import com.agendalc.agendalc.repositories.SolicitudCitaRepository;

import java.util.List;

@Service
public class SolicitudCitaService {

    private final SolicitudCitaRepository solicitudCitaRepository;

    public SolicitudCitaService(SolicitudCitaRepository solicitudCitaRepository) {
        this.solicitudCitaRepository = solicitudCitaRepository;
    }

    public List<SolicitudResponse> obtenerSolicitudesPendientes() {

        List<SolicitudCita> solicitudes = solicitudCitaRepository.findByEstado(SolicitudCita.EstadoSolicitud.PENDIENTE);

        return solicitudes.stream()
                .map(sol -> {

                    SolicitudResponse response = new SolicitudResponse();

                    response.setIdSolicitud(sol.getIdSolicitud());
                    response.setFechaSolicitud(sol.getFechaSolicitud().toLocalDate());
                    response.setAsignadoA(sol.getAsignadoA());
                    response.setRut(sol.getCita().getRut());
                    response.setFechaHora(sol.getCita().getFechaHora());
                    response.setEstadoSolicitud(sol.getEstado().name());
                    response.setEstadoCita(sol.getCita().getEstado().name());

                    return response;
                }).toList();

    }

    @Transactional
    public SolicitudCita asignarSolicitud(Long idSolicitud, String loginUsuario) {
        SolicitudCita solicitud = solicitudCitaRepository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        solicitud.setAsignadoA(loginUsuario);
        return solicitudCitaRepository.save(solicitud);
    }

    public List<SolicitudResponse> obtenerSolicitudesNoAsignadas(){

        List<SolicitudCita> solicitudes = solicitudCitaRepository.findByAsignadoAIsNull();

        return solicitudes.stream()
                 .map(sol -> {
                    SolicitudResponse response = new SolicitudResponse();

                    response.setIdSolicitud(sol.getIdSolicitud());
                    response.setFechaSolicitud(sol.getFechaSolicitud().toLocalDate());
                    response.setAsignadoA(sol.getAsignadoA());
                    response.setRut(sol.getCita().getRut());
                    response.setFechaHora(sol.getCita().getFechaHora());
                    response.setEstadoSolicitud(sol.getEstado().name());
                    response.setEstadoCita(sol.getCita().getEstado().name());

                    return response;
                 }).toList();

    }

}
