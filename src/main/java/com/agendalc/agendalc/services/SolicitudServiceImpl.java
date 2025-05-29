package com.agendalc.agendalc.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.dto.PersonaResponse;
import com.agendalc.agendalc.dto.SolicitudCitaResponse;
import com.agendalc.agendalc.dto.SolicitudRequest;
import com.agendalc.agendalc.dto.SolicitudResponse;
import com.agendalc.agendalc.dto.SolicitudResponseList;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.entities.Solicitud.EstadoSolicitud;
import com.agendalc.agendalc.repositories.SolicitudRepository;
import com.agendalc.agendalc.repositories.TramiteRepository;
import com.agendalc.agendalc.services.interfaces.ApiPersonaService;
import com.agendalc.agendalc.services.interfaces.SolicitudService;

@Service
public class SolicitudServiceImpl implements SolicitudService {

    private final SolicitudRepository solicitudCitaRepository;
    private final ApiPersonaService apiPersonaService;
    private final TramiteRepository tramiteRepository;

    public SolicitudServiceImpl(SolicitudRepository solicitudCitaRepository,
            ApiPersonaService apiPersonaService,
            TramiteRepository tramiteRepository) {
        this.solicitudCitaRepository = solicitudCitaRepository;
        this.apiPersonaService = apiPersonaService;
        this.tramiteRepository = tramiteRepository;
    }

    @Override
    public List<SolicitudResponseList> getSolicitudes() {
        List<Solicitud> solicitudes = solicitudCitaRepository.findAll();

        return solicitudes.stream()
                .map(sol -> {

                    SolicitudResponseList response = new SolicitudResponseList();

                    PersonaResponse personaResponse = apiPersonaService.getPersonaInfo(sol.getRut());

                    String nombre = personaResponse.getNombres() + " ";
                    String paterno = personaResponse.getPaterno() + " ";
                    String materno = personaResponse.getMaterno();

                    response.setNonbre(nombre.concat(paterno).concat(materno));
                    response.setVrut(personaResponse.getVrut());

                    response.setIdSolicitud(sol.getIdSolicitud());
                    response.setFechaSolicitud(sol.getFechaSolicitud());
                    response.setRut(sol.getRut());
                    response.setEstadoSolicitud(sol.getEstado().name());

                    return response;
                }).toList();

    }

    @Override
    public List<SolicitudResponseList> getSolicitudesPendientes() {
        List<Solicitud> solicitudes = solicitudCitaRepository.findByEstado(Solicitud.EstadoSolicitud.PENDIENTE);

        return solicitudes.stream()
                .map(sol -> {

                    SolicitudResponseList response = new SolicitudResponseList();

                    PersonaResponse personaResponse = apiPersonaService.getPersonaInfo(sol.getRut());

                    String nombre = personaResponse.getNombres() + " ";
                    String paterno = personaResponse.getPaterno() + " ";
                    String materno = personaResponse.getMaterno();

                    response.setNonbre(nombre.concat(paterno).concat(materno));
                    response.setVrut(personaResponse.getVrut());

                    response.setIdSolicitud(sol.getIdSolicitud());
                    response.setFechaSolicitud(sol.getFechaSolicitud());
                    response.setRut(sol.getRut());
                    response.setEstadoSolicitud(sol.getEstado().name());

                    return response;
                }).toList();
    }

    @Transactional
    @Override
    public void assignSolicitud(Long idSolicitud, String loginUsuario) {
        Solicitud solicitud = solicitudCitaRepository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        solicitudCitaRepository.save(solicitud);
    }

    @Transactional
    @Override
    public void finishSolicitudById(Long idSolicitud) {
        Solicitud solicitud = solicitudCitaRepository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        solicitud.setEstado(EstadoSolicitud.FINALIZADA);
        solicitudCitaRepository.save(solicitud);
    }

    @Override
    public List<SolicitudCitaResponse> getSolicituCitasByRut(Integer rut) {
        List<Solicitud> citas = solicitudCitaRepository.findByRut(rut);

        return citas.stream().map(cita -> {

            SolicitudCitaResponse response = new SolicitudCitaResponse();

            PersonaResponse personaResponse = apiPersonaService.getPersonaInfo(cita.getRut());

            String nombre = personaResponse.getNombres() + " ";
            String paterno = personaResponse.getPaterno() + " ";
            String materno = personaResponse.getMaterno();

            response.setEstado(cita.getEstado().name());
            response.setFechaSolicitud(cita.getFechaSolicitud());
            response.setRut(cita.getRut());

            response.setNombre(nombre.concat(paterno).concat(materno));
            response.setVrut(personaResponse.getVrut());

            return response;

        }).toList();
    }

    @Override
    public SolicitudResponse createTramite(SolicitudRequest request) {

        Tramite tramite = getTramiteById(request.getIdTramite());

        Solicitud solicitud = convertEntity(request);

        return new SolicitudResponse(solicitudCitaRepository.save(solicitud).getIdSolicitud(), tramite.getNombre(),
                tramite.getIdTramite(), solicitudCitaRepository.save(solicitud).getRut());

    }

    private Tramite getTramiteById(Long idTramite) {
        return tramiteRepository.findById(idTramite)
                .orElseThrow(() -> new IllegalArgumentException("Tramite no encontrado"));
    }

    private Solicitud convertEntity(SolicitudRequest request) {
        Solicitud solicitud = new Solicitud();

        solicitud.setRut(request.getRut());
        solicitud.setTramite(getTramiteById(request.getIdTramite()));

        return solicitud;

    }
}
