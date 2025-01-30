package com.agendalc.agendalc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.repositories.TramiteRepository;

@Service
public class TramiteService {

    private final TramiteRepository tramiteRepository;

    public TramiteService(TramiteRepository tramiteRepository){
        this.tramiteRepository=tramiteRepository;
    }

   @Transactional
    public Tramite crearTramite(Tramite tramite) {
        return tramiteRepository.save(tramite);
    }

    // Obtener todos los trámites
    public List<Tramite> obtenerTodosLosTramites() {
        return tramiteRepository.findAll();
    }

    // Obtener un trámite por ID
    public Optional<Tramite> obtenerTramitePorId(Long id) {
        return tramiteRepository.findById(id);
    }

    @Transactional
    public Tramite actualizarTramite(Long id, Tramite tramite) {
        if (tramiteRepository.existsById(id)) {
            tramite.setIdTramite(id); // Aseguramos que el ID se mantenga
            return tramiteRepository.save(tramite);
        }
        return null; // O lanzar excepción si no existe el trámite
    }

    @Transactional
    public boolean eliminarTramite(Long id) {
        if (tramiteRepository.existsById(id)) {
            tramiteRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
