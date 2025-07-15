package com.agendalc.agendalc.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.dto.ObservacionRequest;
import com.agendalc.agendalc.entities.MovimientoSolicitud;
import com.agendalc.agendalc.entities.ObservacionSolicitud;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.entities.Solicitud.EstadoSolicitud;
import com.agendalc.agendalc.repositories.DocumentoSolicitudRepository;
import com.agendalc.agendalc.repositories.ObservacionSolicitudRepository;
import com.agendalc.agendalc.repositories.SolicitudRepository;
import com.agendalc.agendalc.services.interfaces.ObservacionSolicitudService;
import com.agendalc.agendalc.utils.RepositoryUtils;

@Service
public class ObservacionServiceImpl implements ObservacionSolicitudService {

    private final SolicitudRepository solicitudRepository;

    private final ObservacionSolicitudRepository observacionSolicitudRepository;
    private final DocumentoSolicitudRepository documentoSolicitudRepository;

    public ObservacionServiceImpl(SolicitudRepository solicitudRepository,
            ObservacionSolicitudRepository observacionSolicitudRepository,
            DocumentoSolicitudRepository documentoSolicitudRepository) {
        this.solicitudRepository = solicitudRepository;
        this.observacionSolicitudRepository = observacionSolicitudRepository;
        this.documentoSolicitudRepository = documentoSolicitudRepository;
    }

    @Override
    @Transactional
    public void createObservacion(ObservacionRequest request) {
        Solicitud solicitud = getSolicitud(request.getIdSolicitud());

        ObservacionSolicitud observacion = new ObservacionSolicitud(solicitud, request.getObservacion(),
                request.getLoginUsuario());

        MovimientoSolicitud movimiento = new MovimientoSolicitud(solicitud,
                MovimientoSolicitud.TipoMovimiento.OBSERVACION_AGREGADA, request.getLoginUsuario(),
                request.getLoginUsuario());

        request.getDocumentosAprobados().forEach((id, aprobado) ->

        documentoSolicitudRepository.findById(id)
                .ifPresent(doc -> {
                    doc.setAprobado(aprobado);
                    documentoSolicitudRepository.save(doc);
                })

        );

        solicitud.addObservacion(observacion);
        solicitud.addMovimiento(movimiento);
        changeStateSolicitud(solicitud);

        solicitudRepository.save(solicitud);

    }

    private Solicitud getSolicitud(Long idSolicitud) {
        return RepositoryUtils.findOrThrow(solicitudRepository.findById(idSolicitud),
                String.format("No se encontró la solicitud %d", idSolicitud));

    }

    private Solicitud changeStateSolicitud(Solicitud solicitud) {
        solicitud.setEstado(EstadoSolicitud.OBSERVADA);
        return solicitud;
    }

    @Override
    public void changeCkeckObservacion(Long idObservacion) {
        ObservacionSolicitud observacion = getObservacion(idObservacion);

        observacion.setRevisada(true);

        observacionSolicitudRepository.save(observacion);
    }

    private ObservacionSolicitud getObservacion(Long idObservacion) {

        return RepositoryUtils.findOrThrow(observacionSolicitudRepository.findById(idObservacion),
                String.format("No se encontró la observacion %d", idObservacion));

    }

}
