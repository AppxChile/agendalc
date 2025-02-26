package com.agendalc.agendalc.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agendalc.agendalc.dto.SolicitudResponse;
import com.agendalc.agendalc.services.SolicitudCitaService;

@RestController
@RequestMapping("/agendalc/api/solicitud")
public class SolicitudCitaController {

    private final SolicitudCitaService solicitudCitaService;

    public SolicitudCitaController(SolicitudCitaService solicitudCitaService) {
        this.solicitudCitaService = solicitudCitaService;
    }

    @GetMapping("/entrantes")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> obtenerSolicitudesEntrantes() {

        try {
            List<SolicitudResponse> response = solicitudCitaService.obtenerSolicitudesPendientes();
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> obtenerSolicitudes() {

        try {
            List<SolicitudResponse> response = solicitudCitaService.obtenerSolicitudes();
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PostMapping("/asignar")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> asignarSolicitud(@RequestParam Long idSolicitud, @RequestParam String username) {
        try {
            solicitudCitaService.asignarSolicitud(idSolicitud, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Asignacion creada correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/no-asignadas")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> obtenerSolicitudNoAsignadas() {

        try {
            List<SolicitudResponse> response = solicitudCitaService.obtenerSolicitudesNoAsignadas();
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/asignada/{username}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> obtenerSolicitudAsignada(@PathVariable String username) {

        try {
            List<SolicitudResponse> response = solicitudCitaService.obtenerSolicitudesAsignadas(username);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    
    @PostMapping("/terminar/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> obtenerSolicitudAsignada(@PathVariable Long id) {

        try {
            solicitudCitaService.terminarSolicitud(id);
            return ResponseEntity.status(HttpStatus.CREATED).body((Map.of("message","Solicitud terminada con exito")));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

}
