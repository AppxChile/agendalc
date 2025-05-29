package com.agendalc.agendalc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendalc.agendalc.entities.Solicitud;

import java.util.List;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    List<Solicitud> findByEstado(Solicitud.EstadoSolicitud estado);



    List<Solicitud> findByRut(Integer rut);
}
