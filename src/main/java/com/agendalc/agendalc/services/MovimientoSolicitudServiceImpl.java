package com.agendalc.agendalc.services;

import org.springframework.stereotype.Service;

import com.agendalc.agendalc.dto.MovimientoSolicitudRequest;
import com.agendalc.agendalc.dto.MovimientoSolicitudResponse;
import com.agendalc.agendalc.entities.MovimientoSolicitud;
import com.agendalc.agendalc.entities.Solicitud;
import com.agendalc.agendalc.entities.MovimientoSolicitud.TipoMovimiento;
import com.agendalc.agendalc.repositories.MovimientoSolicitudRepository;
import com.agendalc.agendalc.repositories.SolicitudRepository;
import com.agendalc.agendalc.services.interfaces.MovimientoSolicitudService;

@Service
public class MovimientoSolicitudServiceImpl implements MovimientoSolicitudService {

    private final MovimientoSolicitudRepository movimientoSolicitudRepository;

    private final SolicitudRepository solicitudRepository;

    public MovimientoSolicitudServiceImpl(MovimientoSolicitudRepository movimientoSolicitudRepository,
            SolicitudRepository solicitudRepository) {
        this.movimientoSolicitudRepository = movimientoSolicitudRepository;
        this.solicitudRepository = solicitudRepository;
    }

    @Override
    public MovimientoSolicitudResponse createMovimientoSolicitud(MovimientoSolicitudRequest request) {

        Solicitud solicitud = getSolicitud(request.getIdSolicitud());

        TipoMovimiento tipoMovimiento = getTipoMovimiento(request.getTipoMovimiento());

        String loginUsuario = request.getLoginUsuario();

        MovimientoSolicitud movimientoSolicitud = convertEntity(solicitud, tipoMovimiento, loginUsuario, loginUsuario);

        movimientoSolicitudRepository.save(movimientoSolicitud);

        return new MovimientoSolicitudResponse(solicitud.getIdSolicitud(), loginUsuario, tipoMovimiento.name());

    }

    private Solicitud getSolicitud(Long idSolicitud) {
        return solicitudRepository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));
    }

    private TipoMovimiento getTipoMovimiento(Integer tipoMovimiento) {
        return switch (tipoMovimiento) {
            case 0 -> TipoMovimiento.CREACION;
            case 1 -> TipoMovimiento.ASIGNACION;
            case 2 -> TipoMovimiento.DERIVACION;
            case 3 -> TipoMovimiento.APROBACION;
            case 4 -> TipoMovimiento.RECHAZO;
            case 5 -> TipoMovimiento.OBSERVACION_AGREGADA;
            case 6 -> TipoMovimiento.FINALIZACION;
            case 7 -> TipoMovimiento.OBSERVACION_RESPONDIDA;
            
            default -> throw new IllegalArgumentException("Tipo de movimiento no válido");
        };
    }

    private MovimientoSolicitud convertEntity(Solicitud solicitud, TipoMovimiento tipo, String responsable,
            String asignadoA) {
        return new MovimientoSolicitud(solicitud, tipo, responsable, asignadoA);

    }

}
