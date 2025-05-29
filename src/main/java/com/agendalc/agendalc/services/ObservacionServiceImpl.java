package com.agendalc.agendalc.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.dto.ObservacionRequest;
import com.agendalc.agendalc.entities.MovimientoSolicitud;
import com.agendalc.agendalc.entities.ObservacionSolicitud;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.repositories.SolicitudRepository;
import com.agendalc.agendalc.services.interfaces.ObservacionSolicitudService;

@Service
public class ObservacionServiceImpl implements ObservacionSolicitudService {

    private final SolicitudRepository solicitudRepository;

    public ObservacionServiceImpl(SolicitudRepository solicitudRepository) {
        this.solicitudRepository = solicitudRepository;
    }

    @Override
    @Transactional
    public void createObservacion(ObservacionRequest request) {
        Solicitud solicitud = getSolicitud(request.getIdSolicitud());

        ObservacionSolicitud observacion = new ObservacionSolicitud(solicitud, request.getObservacion(),
                request.getLoginUsuario());

        MovimientoSolicitud movimiento = new MovimientoSolicitud(solicitud,
                MovimientoSolicitud.TipoMovimiento.OBSERVACION_AGREGADA, request.getLoginUsuario(), null);

        solicitud.addObservacion(observacion);
        solicitud.addMovimiento(movimiento);

        solicitudRepository.save(solicitud);

    }

    private Solicitud getSolicitud(Long idSolicitud) {
        return solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));
    }

}
