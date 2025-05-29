package com.agendalc.agendalc.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agendalc.agendalc.dto.SolicitudCitaResponse;
import com.agendalc.agendalc.dto.SolicitudRequest;
import com.agendalc.agendalc.dto.SolicitudResponse;
import com.agendalc.agendalc.dto.SolicitudResponseList;
import com.agendalc.agendalc.services.interfaces.SolicitudService;

@RestController
@RequestMapping("/api/agendalc/solicitud")
@CrossOrigin(origins = "https://dev.appx.cl/")
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudCitaService) {
        this.solicitudService = solicitudCitaService;
    }

    @GetMapping("/entrantes")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> getIncomingSolicitudes() {

        try {
            List<SolicitudResponseList> response = solicitudService.getSolicitudesPendientes();
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PostMapping
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> createSolicitud(@RequestBody SolicitudRequest request) {

        try {
            SolicitudResponse response = solicitudService.createTramite(request);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    

    @GetMapping("/list")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> getSolicitudes() {

        try {
            List<SolicitudResponseList> response = solicitudService.getSolicitudes();
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PostMapping("/asignar")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> assignSolicitud(@RequestParam Long idSolicitud, @RequestParam String username) {
        try {
            solicitudService.assignSolicitud(idSolicitud, username);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Asignacion creada correctamente"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/terminar/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> getSolicitudAssignById(@PathVariable Long id) {

        try {
            solicitudService.finishSolicitudById(id);
            return ResponseEntity.status(HttpStatus.CREATED).body((Map.of("message", "Solicitud terminada con exito")));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @GetMapping("/citas-by-rut/{rut}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> getSolicituCitasByRut(@PathVariable Integer rut) {

        try {
            List<SolicitudCitaResponse> response = solicitudService.getSolicituCitasByRut(rut);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

}
