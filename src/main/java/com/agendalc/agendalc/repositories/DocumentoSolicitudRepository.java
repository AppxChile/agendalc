package com.agendalc.agendalc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agendalc.agendalc.entities.DocumentosSolicitud;
import com.agendalc.agendalc.entities.Solicitud;

public interface DocumentoSolicitudRepository extends JpaRepository<DocumentosSolicitud,Long> {

    void deleteByIdDocumentoSolicitudAndSolicitud(Long idDocuemntoSolicitud, Solicitud solicitud);


}
