package com.agendalc.agendalc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.services.TramiteService;

@RestController
@RequestMapping("/api/agendalc/tramites")
@CrossOrigin(origins = "https://dev.appx.cl/")
public class TramiteController {

    private final TramiteService tramiteService;

    public TramiteController(TramiteService tramiteService) {
        this.tramiteService = tramiteService;
    }

    @PostMapping
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Tramite> createTramite(@RequestBody Tramite tramite) {
        Tramite nuevoTramite = tramiteService.createTramite(tramite);
        return new ResponseEntity<>(nuevoTramite, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<List<Tramite>> getAllTramites() {
        List<Tramite> tramites = tramiteService.getAllTramites();
        return new ResponseEntity<>(tramites, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Tramite> getTramiteById(@PathVariable Long id) {
        Optional<Tramite> tramite = tramiteService.getTramiteById(id);
        return tramite.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Tramite> updateTramite(@PathVariable Long id, @RequestBody Tramite tramite) {
        Tramite tramiteActualizado = tramiteService.updateTramite(id, tramite);
        return tramiteActualizado != null ? new ResponseEntity<>(tramiteActualizado, HttpStatus.OK)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Void> deleteTramite(@PathVariable Long id) {
        boolean eliminado = tramiteService.deleteTramiteById(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
