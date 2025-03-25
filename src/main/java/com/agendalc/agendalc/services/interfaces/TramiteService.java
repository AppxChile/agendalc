package com.agendalc.agendalc.services.interfaces;

import java.util.List;

import com.agendalc.agendalc.entities.Tramite;

public interface TramiteService {

    Tramite createTramite(Tramite tramite);

    List<Tramite> getAllTramites();

    Tramite getTramiteById(Long id);

    Tramite updateTramite(Long id, Tramite tramite);

    boolean deleteTramiteById(Long id);

}
