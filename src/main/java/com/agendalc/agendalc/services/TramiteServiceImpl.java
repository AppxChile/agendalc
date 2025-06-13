package com.agendalc.agendalc.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.dto.DocumentosTramiteResponse;
import com.agendalc.agendalc.dto.TramiteRequest;
import com.agendalc.agendalc.dto.TramiteResponse;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.repositories.TramiteRepository;
import com.agendalc.agendalc.services.interfaces.TramiteService;

@Service
public class TramiteServiceImpl implements TramiteService {

    private final TramiteRepository tramiteRepository;

    public TramiteServiceImpl(TramiteRepository tramiteRepository) {
        this.tramiteRepository = tramiteRepository;
    }

    @Transactional
    @Override
    public Tramite createTramite(TramiteRequest request) {

        Tramite tramite = convertToEntity(request);
        return tramiteRepository.save(tramite);
    }

    @Override
    public List<TramiteResponse> getAllTramites() {
        List<Tramite> tramites = tramiteRepository.findAll();

        return tramites.stream().map(tramite -> {
            TramiteResponse response = new TramiteResponse();
            response.setIdTramite(tramite.getIdTramite());
            response.setNombreTramite(tramite.getNombre());
            response.setDescripcionTramite(tramite.getDescripcion());
            response.setPideDocumentos(tramite.isPideDocumentos());
            response.setRequiereSolicitud(tramite.isRequiereSolicitud());

            List<DocumentosTramiteResponse> documentos = tramite.getDocumentosRequeridos().stream()
                    .map(doc -> new DocumentosTramiteResponse(doc.getIdDocumento(), doc.getNombreDocumento()))
                    .toList();
            response.setDocumentosRequeridos(documentos);

            return response;
        }).toList();
    }

    @Override
    public Tramite getTramiteById(Long id) {
        return tramiteRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("no existe el id"));
    }

    @Transactional
    @Override
    public Tramite updateTramite(Long id, Tramite tramite) {
        if (tramiteRepository.existsById(id)) {
            tramite.setIdTramite(id);
            return tramiteRepository.save(tramite);
        }
        return null;
    }

    @Override
    public boolean deleteTramiteById(Long id) {
        if (tramiteRepository.existsById(id)) {
            tramiteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private Tramite convertToEntity(TramiteRequest request) {
        Tramite tramite = new Tramite();
        tramite.setNombre(request.getNombre().toUpperCase());
        tramite.setDescripcion(request.getDescripcion());
        tramite.setPideDocumentos(request.isPideDocumentos());
        tramite.setRequiereSolicitud(request.isRequiereSolicitud());
        return tramite;
    }

}
