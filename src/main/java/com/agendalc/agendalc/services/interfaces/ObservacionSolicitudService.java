package com.agendalc.agendalc.services.interfaces;

import com.agendalc.agendalc.dto.ObservacionRequest;

public interface ObservacionSolicitudService {

    void createObservacion(ObservacionRequest request);

    void changeCkeckObservacion(Long idObservacion);

}
