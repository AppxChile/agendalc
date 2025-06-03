package com.agendalc.agendalc.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.agendalc.agendalc.dto.DocumentosSubidosRequest;
import com.agendalc.agendalc.dto.MovimientoSolicitudRequest;
import com.agendalc.agendalc.dto.MovimientosDto;
import com.agendalc.agendalc.dto.ObservacionesDto;
import com.agendalc.agendalc.dto.PersonaResponse;
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
import com.agendalc.agendalc.services.interfaces.MovimientoSolicitudService;
import com.agendalc.agendalc.services.interfaces.SolicitudService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class SolicitudServiceImpl implements SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final ApiPersonaService apiPersonaService;
    private final TramiteRepository tramiteRepository;
    private final ArchivoService archivoService;
    private final DocumentosTramiteRepository documentosTramiteRepository;
    private final MovimientoSolicitudService movimientoSolicitudService;

    public SolicitudServiceImpl(SolicitudRepository solicitudCitaRepository, ArchivoService archivoService,
            ApiPersonaService apiPersonaService,
            TramiteRepository tramiteRepository, DocumentosTramiteRepository documentosTramiteRepository,
            MovimientoSolicitudService movimientoSolicitudService) {
        this.solicitudRepository = solicitudCitaRepository;
        this.apiPersonaService = apiPersonaService;
        this.tramiteRepository = tramiteRepository;
        this.archivoService = archivoService;
        this.documentosTramiteRepository = documentosTramiteRepository;
        this.movimientoSolicitudService = movimientoSolicitudService;
    }

    @Override
    public List<SolicitudResponseList> getSolicitudes(int year) {
        List<Solicitud> solicitudes = solicitudRepository
                .findByFechaSolicitudYearWithMovimientosOrdered(year);

        return mapToSolicitudResponseList(solicitudes).stream()
                .filter(sol -> sol.getEstadoSolicitud().equals(EstadoSolicitud.PENDIENTE.toString()))
                .toList();

    }

    @Transactional
    @Override
    public void assignOrDerivateSolicitud(Long idSolicitud, String loginUsuario, String asignadoA, int tipo) {
        Solicitud solicitud = getSolicitudById(idSolicitud);

        solicitud.setEstado(EstadoSolicitud.ASIGNADA);
        solicitud.setAsignadoA(asignadoA);

        MovimientoSolicitudRequest movimiento = mapMovimientoSolicitudRequest(idSolicitud, tipo, loginUsuario,
                asignadoA);

        movimientoSolicitudService.createMovimientoSolicitud(movimiento);

        solicitudRepository.save(solicitud);
    }

    private MovimientoSolicitudRequest mapMovimientoSolicitudRequest(Long idSolicitud, Integer tipMovimiento,
            String usuario, String asignadoA) {
        MovimientoSolicitudRequest movimiento = new MovimientoSolicitudRequest();
        movimiento.setIdSolicitud(idSolicitud);
        movimiento.setTipoMovimiento(tipMovimiento);
        movimiento.setLoginUsuario(usuario);
        movimiento.setAsignadoA(asignadoA);

        return movimiento;
    }

    @Transactional
    @Override
    public void finishSolicitudById(Long idSolicitud, String loginUsuario) {
        Solicitud solicitud = getSolicitudById(idSolicitud);

        MovimientoSolicitudRequest movimiento = mapMovimientoSolicitudRequest(idSolicitud, 6, loginUsuario,
                null);

        movimientoSolicitudService.createMovimientoSolicitud(movimiento);

        solicitud.setEstado(EstadoSolicitud.FINALIZADA);
        solicitudRepository.save(solicitud);
    }

    @Override
    public List<SolicitudResponseList> getSolicitudesByRut(Integer rut) {
        List<Solicitud> solicitudes = solicitudRepository.findByRut(rut);

        return mapToSolicitudResponseList(solicitudes);
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

    @Override
    public List<SolicitudResponseList> getSolicitudesByRutFunc(Integer rut) {
        List<Solicitud> solicitudes = solicitudRepository.findByAsignadoA(rut.toString());

        return mapToSolicitudResponseList(solicitudes);

    }

    private List<SolicitudResponseList> mapToSolicitudResponseList(List<Solicitud> solicitudes) {
        return solicitudes.stream()
                .map(sol -> {

                    SolicitudResponseList response = new SolicitudResponseList();

                    PersonaResponse personaResponse = apiPersonaService.getPersonaInfo(sol.getRut());

                    response.setNonbre(personaResponse.getNombreCompleto());
                    response.setVrut(personaResponse.getVrut());

                    response.setIdSolicitud(sol.getIdSolicitud());
                    response.setFechaSolicitud(sol.getFechaSolicitud());
                    response.setRut(sol.getRut());
                    response.setEstadoSolicitud(sol.getEstado().name());

                    if (sol.getMovimientos() != null && !sol.getMovimientos().isEmpty()) {
                        response.setMovimientos(sol.getMovimientos().stream()
                                .map(mov -> {
                                    MovimientosDto movDto = new MovimientosDto();
                                    movDto.setIdMovimiento(mov.getIdMovimiento());
                                    movDto.setTipoMovimiento(mov.getTipo().name());
                                    movDto.setUsuarioResponsable(mov.getUsuarioResponsable());
                                    movDto.setFechaMovimiento(mov.getFechaMovimiento().toString());
                                    return movDto;
                                })
                                .collect(Collectors.toSet()));
                    }

                    if (sol.getObservaciones() != null && !sol.getObservaciones().isEmpty()) {
                        response.setObservaciones(sol.getObservaciones().stream().map(obs -> {
                            ObservacionesDto obsDto = new ObservacionesDto();
                            obsDto.setIdObservacion(obs.getIdObservacion());
                            obsDto.setGlosa(obs.getGlosa());
                            obsDto.setFechaObservacion(obs.getFechaObservacion().toString());
                            obsDto.setUsuarioResponsable(obs.getUsuarioResponsable());
                            obsDto.setRevisada(obs.isRevisada());
                            return obsDto;
                        }).collect(Collectors.toSet()));
                    }

                    return response;
                }).toList();
    }

    private Solicitud getSolicitudById(Long idSolicitud) {
        return solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada con ID: " + idSolicitud));
    }

}