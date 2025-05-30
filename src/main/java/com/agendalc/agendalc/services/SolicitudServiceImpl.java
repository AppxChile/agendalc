package com.agendalc.agendalc.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.agendalc.agendalc.dto.DocumentosSubidosRequest;
import com.agendalc.agendalc.dto.PersonaResponse;
import com.agendalc.agendalc.dto.SolicitudCitaResponse;
import com.agendalc.agendalc.dto.SolicitudRequest;
import com.agendalc.agendalc.dto.SolicitudResponse;
import com.agendalc.agendalc.dto.SolicitudResponseList;
import com.agendalc.agendalc.entities.DocumentosSolicitud;
import com.agendalc.agendalc.entities.DocumentosTramite;
import com.agendalc.agendalc.entities.MovimientoSolicitud;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.entities.MovimientoSolicitud.TipoMovimiento;
import com.agendalc.agendalc.entities.Solicitud.EstadoSolicitud;
import com.agendalc.agendalc.repositories.DocumentosTramiteRepository;
import com.agendalc.agendalc.repositories.SolicitudRepository;
import com.agendalc.agendalc.repositories.TramiteRepository;
import com.agendalc.agendalc.services.interfaces.ApiPersonaService;
import com.agendalc.agendalc.services.interfaces.ArchivoService;
import com.agendalc.agendalc.services.interfaces.SolicitudService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SolicitudServiceImpl implements SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final ApiPersonaService apiPersonaService;
    private final TramiteRepository tramiteRepository;
    private final ArchivoService archivoService;
    private final DocumentosTramiteRepository documentosTramiteRepository;

    public SolicitudServiceImpl(SolicitudRepository solicitudCitaRepository, ArchivoService archivoService,
            ApiPersonaService apiPersonaService,
            TramiteRepository tramiteRepository, DocumentosTramiteRepository documentosTramiteRepository) {
        this.solicitudRepository = solicitudCitaRepository;
        this.apiPersonaService = apiPersonaService;
        this.tramiteRepository = tramiteRepository;
        this.archivoService = archivoService;
        this.documentosTramiteRepository = documentosTramiteRepository;
    }

    @Override
    public List<SolicitudResponseList> getSolicitudes() {
        List<Solicitud> solicitudes = solicitudRepository.findAll();

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
        List<Solicitud> solicitudes = solicitudRepository.findByEstado(Solicitud.EstadoSolicitud.PENDIENTE);

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
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        solicitudRepository.save(solicitud);
    }

    @Transactional
    @Override
    public void finishSolicitudById(Long idSolicitud) {
        Solicitud solicitud = solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        solicitud.setEstado(EstadoSolicitud.FINALIZADA);
        solicitudRepository.save(solicitud);
    }

    @Override
    public List<SolicitudCitaResponse> getSolicituCitasByRut(Integer rut) {
        List<Solicitud> citas = solicitudRepository.findByRut(rut);

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
    @Transactional
    public SolicitudResponse createSolicitud(SolicitudRequest request) throws IOException {
        Tramite tramite = getTramiteById(request.getIdTramite());

        Solicitud solicitud = new Solicitud();
        solicitud.setRut(request.getRut());
        solicitud.setTramite(tramite);

        MovimientoSolicitud primerMovimiento = firstMovement(solicitud, TipoMovimiento.CREACION);

        if (request.getDocumentos() != null && !request.getDocumentos().isEmpty()) {
            if (solicitud.getDocumentosEntregados() == null) {
                solicitud.setDocumentosEntregados(new ArrayList<>());
            }

            for (DocumentosSubidosRequest docRequest : request.getDocumentos()) {
                MultipartFile file = docRequest.getFile();

                if (file != null && !file.isEmpty()) {
                    DocumentosTramite tipoDocumentoRequerido = documentosTramiteRepository
                            .findById(docRequest.getIdTipoDocumento())
                            .orElseThrow(() -> new EntityNotFoundException("Tipo de documento requerido con ID "
                                    + docRequest.getIdTipoDocumento() + " no encontrado."));

                    String nombreGuardado = archivoService.guardarArchivo(file);
                    Path rutaCompleta = archivoService.getRutaCompletaArchivo(nombreGuardado);

                    DocumentosSolicitud documento = new DocumentosSolicitud(
                            solicitud,
                            tipoDocumentoRequerido,
                            rutaCompleta.toString());

                    solicitud.getDocumentosEntregados().add(documento);
                    documento.setSolicitud(solicitud);
                }
            }
        }

        solicitud.addMovimiento(primerMovimiento);

        solicitud = solicitudRepository.save(solicitud);

        return new SolicitudResponse(
                solicitud.getIdSolicitud(),
                tramite.getNombre(),
                tramite.getIdTramite(),
                solicitud.getRut());
    }

    private Tramite getTramiteById(Long idTramite) {
        return tramiteRepository.findById(idTramite)
                .orElseThrow(() -> new IllegalArgumentException("Trámite no encontrado"));
    }

    private MovimientoSolicitud firstMovement(Solicitud solicitud, TipoMovimiento tipo) {
        return new MovimientoSolicitud(
                solicitud,
                tipo,
                null,
                null);

    }

}
