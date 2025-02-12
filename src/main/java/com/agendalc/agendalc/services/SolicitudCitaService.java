package com.agendalc.agendalc.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.agendalc.agendalc.entities.SolicitudCita;
import com.agendalc.agendalc.repositories.SolicitudCitaRepository;

import java.util.List;

@Service
public class SolicitudCitaService {

    private final SolicitudCitaRepository solicitudCitaRepository;

    public SolicitudCitaService(SolicitudCitaRepository solicitudCitaRepository) {
        this.solicitudCitaRepository = solicitudCitaRepository;
    }

    public List<SolicitudCita> obtenerSolicitudesPendientes() {
        return solicitudCitaRepository.findByEstado(SolicitudCita.EstadoSolicitud.PENDIENTE);
    }

    @Transactional
    public SolicitudCita asignarSolicitud(Long idSolicitud, String loginUsuario) {
        SolicitudCita solicitud = solicitudCitaRepository.findById(idSolicitud)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));
        
        solicitud.setAsignadoA(loginUsuario);
        return solicitudCitaRepository.save(solicitud);
    }
}
