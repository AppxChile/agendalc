package com.agendalc.agendalc.controllers;

import java.util.List;

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

import com.agendalc.agendalc.dto.TramiteRequest;
import com.agendalc.agendalc.dto.TramiteResponse;
import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.services.interfaces.TramiteService;

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
    public ResponseEntity<Object> createTramite(@RequestBody TramiteRequest request) {
        try {
            Tramite nuevoTramite = tramiteService.createTramite(request);
            return new ResponseEntity<>(nuevoTramite, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> getAllTramites() {

        try {
            List<TramiteResponse> tramites = tramiteService.getAllTramites();
            return new ResponseEntity<>(tramites, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> getTramiteById(@PathVariable Long id) {
        try {
            Tramite tramite = tramiteService.getTramiteById(id);
            return new ResponseEntity<>(tramite, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> updateTramite(@PathVariable Long id, @RequestBody Tramite tramite) {

        try {
            Tramite tramiteActualizado = tramiteService.updateTramite(id, tramite);
            return new ResponseEntity<>(tramiteActualizado, HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Void> deleteTramite(@PathVariable Long id) {
        boolean eliminado = tramiteService.deleteTramiteById(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
