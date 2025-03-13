package com.agendalc.agendalc.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.dto.PersonaResponse;
import com.agendalc.agendalc.dto.SolicitudCitaResponse;
import com.agendalc.agendalc.dto.SolicitudResponse;
import com.agendalc.agendalc.entities.SolicitudCita;
import com.agendalc.agendalc.entities.SolicitudCita.EstadoSolicitud;
import com.agendalc.agendalc.repositories.SolicitudCitaRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class SolicitudCitaService {

    private final SolicitudCitaRepository solicitudCitaRepository;

    private final ApiService apiService;

    public SolicitudCitaService(SolicitudCitaRepository solicitudCitaRepository, ApiService apiService) {
        this.solicitudCitaRepository = solicitudCitaRepository;
        this.apiService = apiService;
    }

    public List<SolicitudResponse> getSolicitudes() {

        List<SolicitudCita> solicitudes = solicitudCitaRepository.findAll();

        return solicitudes.stream()
                .map(sol -> {

                    SolicitudResponse response = new SolicitudResponse();

                    PersonaResponse personaResponse = apiService.getPersonaInfo(sol.getCita().getRut());

                    String nombre = personaResponse.getNombres() + " ";
                    String paterno = personaResponse.getPaterno() + " ";
                    String materno = personaResponse.getMaterno();

                    response.setNonbre(nombre.concat(paterno).concat(materno));
                    response.setVrut(personaResponse.getVrut());

                    response.setIdSolicitud(sol.getIdSolicitud());
                    response.setFechaSolicitud(sol.getFechaSolicitud());
                    response.setAsignadoA(sol.getAsignadoA());
                    response.setRut(sol.getCita().getRut());
                    response.setFechaHoraCita(sol.getCita().getFechaHora());
                    response.setEstadoSolicitud(sol.getEstado().name());
                    response.setFechaFinalizacion(sol.getFechaFinalizacion());

                    return response;
                }).toList();

    }

    public List<SolicitudResponse> getSolicitudesPendientes() {

        List<SolicitudCita> solicitudes = solicitudCitaRepository.findByEstado(SolicitudCita.EstadoSolicitud.PENDIENTE);

        return solicitudes.stream()
                .map(sol -> {

                    SolicitudResponse response = new SolicitudResponse();

                    PersonaResponse personaResponse = apiService.getPersonaInfo(sol.getCita().getRut());

                    String nombre = personaResponse.getNombres() + " ";
                    String paterno = personaResponse.getPaterno() + " ";
                    String materno = personaResponse.getMaterno();

                    response.setNonbre(nombre.concat(paterno).concat(materno));
                    response.setVrut(personaResponse.getVrut());

                    response.setIdSolicitud(sol.getIdSolicitud());
                    response.setFechaSolicitud(sol.getFechaSolicitud());
                    response.setAsignadoA(sol.getAsignadoA());
                    response.setRut(sol.getCita().getRut());
                    response.setFechaHoraCita(sol.getCita().getFechaHora());
                    response.setEstadoSolicitud(sol.getEstado().name());
                    response.setFechaFinalizacion(sol.getFechaFinalizacion());

                    return response;
                }).toList();

    }

    @Transactional
    public void assignSolicitud(Long idSolicitud, String loginUsuario) {
        SolicitudCita solicitud = solicitudCitaRepository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        solicitud.setAsignadoA(loginUsuario);
        solicitudCitaRepository.save(solicitud);
    }

    @Transactional
    public void finishSolicitudById(Long idSolicitud) {
        SolicitudCita solicitud = solicitudCitaRepository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        solicitud.setEstado(EstadoSolicitud.FINALIZADA);
        solicitud.setFechaFinalizacion(LocalDate.now());
        solicitudCitaRepository.save(solicitud);
    }

    public List<SolicitudResponse> getSolicitudesUnassigned() {

        List<SolicitudCita> solicitudes = solicitudCitaRepository.findByAsignadoAIsNull();

        return solicitudes.stream()
                .map(sol -> {
                    SolicitudResponse response = new SolicitudResponse();

                    response.setRut(sol.getCita().getRut());

                    PersonaResponse personaResponse = apiService.getPersonaInfo(sol.getCita().getRut());

                    String nombre = personaResponse.getNombres() + " ";
                    String paterno = personaResponse.getPaterno() + " ";
                    String materno = personaResponse.getMaterno();

                    response.setNonbre(nombre.concat(paterno).concat(materno));
                    response.setVrut(personaResponse.getVrut());

                    response.setIdSolicitud(sol.getIdSolicitud());
                    response.setFechaSolicitud(sol.getFechaSolicitud());
                    response.setAsignadoA(sol.getAsignadoA());

                    response.setFechaHoraCita(sol.getCita().getFechaHora());
                    response.setEstadoSolicitud(sol.getEstado().name());

                    return response;
                }).toList();

    }

    public List<SolicitudResponse> getSolicitudesAssignByUser(String username) {

        List<SolicitudCita> solicitudes = solicitudCitaRepository.findByAsignadoA(username);

        return solicitudes.stream()
                .map(sol -> {
                    SolicitudResponse response = new SolicitudResponse();

                    response.setRut(sol.getCita().getRut());

                    PersonaResponse personaResponse = apiService.getPersonaInfo(sol.getCita().getRut());

                    String nombre = personaResponse.getNombres() + " ";
                    String paterno = personaResponse.getPaterno() + " ";
                    String materno = personaResponse.getMaterno();

                    response.setNonbre(nombre.concat(paterno).concat(materno));
                    response.setVrut(personaResponse.getVrut());

                    response.setIdSolicitud(sol.getIdSolicitud());
                    response.setFechaSolicitud(sol.getFechaSolicitud());
                    response.setAsignadoA(sol.getAsignadoA());

                    response.setFechaHoraCita(sol.getCita().getFechaHora());
                    response.setEstadoSolicitud(sol.getEstado().name());

                    return response;
                }).toList();

    }

    public List<SolicitudCitaResponse> getSolicituCitasByRut(Integer rut) {

        List<SolicitudCita> citas = solicitudCitaRepository.findByCitaRut(rut);

        return citas.stream().map(cita -> {

            SolicitudCitaResponse response = new SolicitudCitaResponse();

            PersonaResponse personaResponse = apiService.getPersonaInfo(cita.getCita().getRut());

            String nombre = personaResponse.getNombres() + " ";
            String paterno = personaResponse.getPaterno() + " ";
            String materno = personaResponse.getMaterno();

            response.setEstado(cita.getEstado().name());
            response.setFechaSolicitud(cita.getFechaSolicitud());
            response.setRut(cita.getCita().getRut());
            response.setFechaAgenda(cita.getCita().getAgenda().getFecha());
            response.setIdBloque(cita.getCita().getBloqueHorario().getIdBloque());
            response.setHoraInicioBloque(cita.getCita().getBloqueHorario().getHoraInicio());
            response.setHoraFinBloque(cita.getCita().getBloqueHorario().getHoraFin());

            response.setNombre(nombre.concat(paterno).concat(materno));
            response.setVrut(personaResponse.getVrut());

            return response;

        }).toList();
    }

}
