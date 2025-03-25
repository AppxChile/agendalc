package com.agendalc.agendalc.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Tramite createTramite(Tramite tramite) {
        return tramiteRepository.save(tramite);
    }

    @Override
    public List<Tramite> getAllTramites() {
        return tramiteRepository.findAll();
    }

    @Override
    public Tramite getTramiteById(Long id) {
        return tramiteRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("no existe el id"));
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

}
