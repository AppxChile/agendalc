package com.agendalc.agendalc.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agendalc.agendalc.dto.SolicitudResponse;
import com.agendalc.agendalc.services.SolicitudCitaService;

@RestController
@RequestMapping("/api/solicitud")
public class SolicitudCitaController {

    private final SolicitudCitaService solicitudCitaService;

    public SolicitudCitaController(SolicitudCitaService solicitudCitaService) {
        this.solicitudCitaService = solicitudCitaService;
    }

    @GetMapping("/entrantes")
    public ResponseEntity<Object> obtenerSolicitudesEntrantes() {

        try {
           List<SolicitudResponse> response = solicitudCitaService.obtenerSolicitudesPendientes();
           return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
     
    }

    @GetMapping("/no-asignadas")
    public ResponseEntity<Object> obtenerSolicitudNoAsignadas() {

        try {
           List<SolicitudResponse> response = solicitudCitaService.obtenerSolicitudesNoAsignadas();
           return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
     
    }

}
