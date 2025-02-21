package com.agendalc.agendalc.controllers;

import com.agendalc.agendalc.dto.CitaDto;
import com.agendalc.agendalc.dto.CitaRequest;
import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.services.CitaService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private static final String ERROR_KEY = "error";

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    
    @PostMapping
    public ResponseEntity<Object> crearCita(@RequestBody CitaRequest citaRequest) {
        try {
            CitaDto nuevaCita = citaService.crearCita(citaRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaCita);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of(ERROR_KEY, "No se encontró la agenda o bloque horario"+ e.getMessage()));

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
    public ResponseEntity<CitaDto> obtenerCita(@PathVariable Long id) {
        Optional<Cita> citaOptional = citaService.obtenerCita(id);
        return citaOptional.map(cita -> new ResponseEntity<>(new CitaDto(cita), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cita> actualizarCita(@PathVariable Long id, @RequestBody Cita cita) {
        Cita citaActualizada = citaService.actualizarCita(id, cita);
        return citaActualizada != null
                ? new ResponseEntity<>(citaActualizada, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCita(@PathVariable Long id) {
        boolean eliminado = citaService.eliminarCita(id);
        return eliminado ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
