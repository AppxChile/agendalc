package com.agendalc.agendalc.controllers;

import com.agendalc.agendalc.dto.CitaDto;
import com.agendalc.agendalc.dto.CitaRequest;
import com.agendalc.agendalc.dto.SolicitudCitaResponse;
import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.services.interfaces.CitaService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/agendalc/citas")
@CrossOrigin(origins = "https://dev.appx.cl/")
public class CitaController {

    private static final String ERROR_KEY = "error";

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> createCita(@RequestBody CitaRequest citaRequest) {
        try {
            CitaDto nuevaCita = citaService.createCita(citaRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCita);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(ERROR_KEY, "No se encontró la agenda o bloque horario" + e.getMessage()));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(ERROR_KEY, "Datos inválidos en la solicitud"));

        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of(ERROR_KEY, "No hay cupos disponibles en este bloque horario"));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(ERROR_KEY, e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> getCita(@PathVariable Long id) {

        try {
            Cita cita = citaService.findById(id);
            return ResponseEntity.ok(cita);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Cita> updateCita(@PathVariable Long id, @RequestBody Cita cita) {
        Cita citaActualizada = citaService.updateCita(id, cita);
        return citaActualizada != null
                ? new ResponseEntity<>(citaActualizada, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Void> deleteCita(@PathVariable Long id) {
        boolean eliminado = citaService.deleteCitaById(id);
        return eliminado ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/findByRut/{rut}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> getCitabyRut(@PathVariable Integer rut) {
        try {
            List<SolicitudCitaResponse> response = citaService.getCitaByRut(rut);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }
}
