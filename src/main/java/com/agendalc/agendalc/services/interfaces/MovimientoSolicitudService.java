package com.agendalc.agendalc.services.interfaces;

import com.agendalc.agendalc.dto.MovimientoSolicitudRequest;
import com.agendalc.agendalc.dto.MovimientoSolicitudResponse;

public interface MovimientoSolicitudService {

    MovimientoSolicitudResponse createMovimientoSolicitud(MovimientoSolicitudRequest request);

}
