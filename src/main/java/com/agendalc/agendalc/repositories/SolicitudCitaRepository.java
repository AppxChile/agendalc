package com.agendalc.agendalc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendalc.agendalc.entities.SolicitudCita;

import java.util.List;

public interface SolicitudCitaRepository extends JpaRepository<SolicitudCita, Long> {
    List<SolicitudCita> findByEstado(SolicitudCita.EstadoSolicitud estado);

    List<SolicitudCita> findByAsignadoAIsNull();

}
