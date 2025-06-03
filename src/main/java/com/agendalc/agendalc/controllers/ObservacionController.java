package com.agendalc.agendalc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agendalc.agendalc.dto.ObservacionRequest;
import com.agendalc.agendalc.services.interfaces.ObservacionSolicitudService;

@RestController
@RequestMapping("/api/agendalc/observaciones")
public class ObservacionController {

    private final ObservacionSolicitudService observacionSolicitudService;

    public ObservacionController(ObservacionSolicitudService observacionSolicitudService) {
        this.observacionSolicitudService = observacionSolicitudService;
    }

    @PostMapping
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> createObservacion(@RequestBody ObservacionRequest request) {
        try {
            observacionSolicitudService.createObservacion(request);
            return new ResponseEntity<>(HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/check")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> checkObservacion(@RequestParam Long idObservacion) {
        try {
            observacionSolicitudService.changeCkeckObservacion(idObservacion);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}