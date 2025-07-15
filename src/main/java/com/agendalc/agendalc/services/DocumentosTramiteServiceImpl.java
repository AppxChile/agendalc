package com.agendalc.agendalc.services;

import org.springframework.stereotype.Service;

import com.agendalc.agendalc.dto.DocumentosTramiteRequest;
import com.agendalc.agendalc.dto.DocumentosTramiteResponse;
import com.agendalc.agendalc.entities.DocumentosTramite;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.repositories.DocumentosTramiteRepository;
import com.agendalc.agendalc.repositories.TramiteRepository;
import com.agendalc.agendalc.services.interfaces.DocumentosTramiteService;
import com.agendalc.agendalc.utils.RepositoryUtils;

@Service
public class DocumentosTramiteServiceImpl implements DocumentosTramiteService {

    private final DocumentosTramiteRepository documentosTramiteRepository;

    private final TramiteRepository tramiteRepository;

    public DocumentosTramiteServiceImpl(DocumentosTramiteRepository documentosTramiteRepository,
            TramiteRepository tramiteRepository) {
        this.documentosTramiteRepository = documentosTramiteRepository;
        this.tramiteRepository = tramiteRepository;
    }

    @Override
    public DocumentosTramiteResponse createDocumentosTramite(DocumentosTramiteRequest request) {

        Tramite tramite = getTramiteById(request.getIdTramite());

        DocumentosTramite documentosTramite = documentosTramiteRepository.save(convertToEntity(request, tramite));

        return new DocumentosTramiteResponse(documentosTramite.getIdDocumento(),
                documentosTramite.getNombreDocumento());

    }

    private DocumentosTramite convertToEntity(DocumentosTramiteRequest request, Tramite tramite) {
        DocumentosTramite documentosTramite = new DocumentosTramite();
        documentosTramite.setNombreDocumento(request.getNombreDocumento());
        documentosTramite.setTramite(tramite);
        return documentosTramite;
    }

    private Tramite getTramiteById(Long id) {

        return RepositoryUtils.findOrThrow(tramiteRepository.findById(id),
                String.format("No se encontró el trámite con el id %d", id));

    }

}
