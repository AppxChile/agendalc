package com.agendalc.agendalc.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.dto.PersonaResponse;
import com.agendalc.agendalc.dto.SolicitudResponse;
import com.agendalc.agendalc.entities.SolicitudCita;
import com.agendalc.agendalc.entities.SolicitudCita.EstadoSolicitud;
import com.agendalc.agendalc.repositories.SolicitudCitaRepository;

import java.util.List;

@Service
public class SolicitudCitaService {

    private final SolicitudCitaRepository solicitudCitaRepository;

    private final ApiService apiService;

    public SolicitudCitaService(SolicitudCitaRepository solicitudCitaRepository, ApiService apiService) {
        this.solicitudCitaRepository = solicitudCitaRepository;
        this.apiService = apiService;
    }

    public List<SolicitudResponse> obtenerSolicitudes() {

        List<SolicitudCita> solicitudes = solicitudCitaRepository.findAll();

        return solicitudes.stream()
                .map(sol -> {

                    SolicitudResponse response = new SolicitudResponse();

                    PersonaResponse personaResponse = apiService.obtenerDatos(sol.getCita().getRut());

                    String nombre = personaResponse.getNombres() + " ";
                    String paterno = personaResponse.getPaterno() + " ";
                    String materno = personaResponse.getMaterno();

                    response.setNonbre(nombre.concat(paterno).concat(materno));
                    response.setVrut(personaResponse.getVrut());

                    response.setIdSolicitud(sol.getIdSolicitud());
                    response.setFechaSolicitud(sol.getFechaSolicitud().toLocalDate());
                    response.setAsignadoA(sol.getAsignadoA());
                    response.setRut(sol.getCita().getRut());
                    response.setFechaHoraCita(sol.getCita().getFechaHora());
                    response.setEstadoSolicitud(sol.getEstado().name());

                    return response;
                }).toList();

    }

    public List<SolicitudResponse> obtenerSolicitudesPendientes() {

        List<SolicitudCita> solicitudes = solicitudCitaRepository.findByEstado(SolicitudCita.EstadoSolicitud.PENDIENTE);

        return solicitudes.stream()
                .map(sol -> {

                    SolicitudResponse response = new SolicitudResponse();

                    PersonaResponse personaResponse = apiService.obtenerDatos(sol.getCita().getRut());

                    String nombre = personaResponse.getNombres() + " ";
                    String paterno = personaResponse.getPaterno() + " ";
                    String materno = personaResponse.getMaterno();

                    response.setNonbre(nombre.concat(paterno).concat(materno));
                    response.setVrut(personaResponse.getVrut());

                    response.setIdSolicitud(sol.getIdSolicitud());
                    response.setFechaSolicitud(sol.getFechaSolicitud().toLocalDate());
                    response.setAsignadoA(sol.getAsignadoA());
                    response.setRut(sol.getCita().getRut());
                    response.setFechaHoraCita(sol.getCita().getFechaHora());
                    response.setEstadoSolicitud(sol.getEstado().name());

                    return response;
                }).toList();

    }

    @Transactional
    public void asignarSolicitud(Long idSolicitud, String loginUsuario) {
        SolicitudCita solicitud = solicitudCitaRepository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        solicitud.setAsignadoA(loginUsuario);
        solicitudCitaRepository.save(solicitud);
    }



    @Transactional
    public void terminarSolicitud(Long idSolicitud) {
        SolicitudCita solicitud = solicitudCitaRepository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        solicitud.setEstado(EstadoSolicitud.FINALIZADA);
        solicitudCitaRepository.save(solicitud);
    }

    public List<SolicitudResponse> obtenerSolicitudesNoAsignadas() {

        List<SolicitudCita> solicitudes = solicitudCitaRepository.findByAsignadoAIsNull();

        return solicitudes.stream()
                .map(sol -> {
                    SolicitudResponse response = new SolicitudResponse();

                    response.setRut(sol.getCita().getRut());

                    PersonaResponse personaResponse = apiService.obtenerDatos(sol.getCita().getRut());

                    String nombre = personaResponse.getNombres() + " ";
                    String paterno = personaResponse.getPaterno() + " ";
                    String materno = personaResponse.getMaterno();

                    response.setNonbre(nombre.concat(paterno).concat(materno));
                    response.setVrut(personaResponse.getVrut());

                    response.setIdSolicitud(sol.getIdSolicitud());
                    response.setFechaSolicitud(sol.getFechaSolicitud().toLocalDate());
                    response.setAsignadoA(sol.getAsignadoA());

                    response.setFechaHoraCita(sol.getCita().getFechaHora());
                    response.setEstadoSolicitud(sol.getEstado().name());

                    return response;
                }).toList();

    }

    public List<SolicitudResponse> obtenerSolicitudesAsignadas(String username) {

        List<SolicitudCita> solicitudes = solicitudCitaRepository.findByAsignadoA(username);

        return solicitudes.stream()
                .map(sol -> {
                    SolicitudResponse response = new SolicitudResponse();

                    response.setRut(sol.getCita().getRut());

                    PersonaResponse personaResponse = apiService.obtenerDatos(sol.getCita().getRut());

                    String nombre = personaResponse.getNombres() + " ";
                    String paterno = personaResponse.getPaterno() + " ";
                    String materno = personaResponse.getMaterno();

                    response.setNonbre(nombre.concat(paterno).concat(materno));
                    response.setVrut(personaResponse.getVrut());

                    response.setIdSolicitud(sol.getIdSolicitud());
                    response.setFechaSolicitud(sol.getFechaSolicitud().toLocalDate());
                    response.setAsignadoA(sol.getAsignadoA());

                    response.setFechaHoraCita(sol.getCita().getFechaHora());
                    response.setEstadoSolicitud(sol.getEstado().name());

                    return response;
                }).toList();

    }

}
