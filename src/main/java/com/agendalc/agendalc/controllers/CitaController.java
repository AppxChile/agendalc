package com.agendalc.agendalc.controllers;

import com.agendalc.agendalc.controllers.dto.CitaDto;
import com.agendalc.agendalc.controllers.dto.CitaRequest;
import com.agendalc.agendalc.entities.Cita;
import com.agendalc.agendalc.services.CitaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService){
        this.citaService = citaService;
    }

    // Crear una nueva cita
    @PostMapping
    public ResponseEntity<Cita> crearCita(@RequestBody CitaRequest citaRequest) {
        Cita nuevaCita = citaService.crearCita(citaRequest);
        return ResponseEntity.status(201).body(nuevaCita);
    }

    // Obtener una cita por su id
    @GetMapping("/{id}")
    public ResponseEntity<CitaDto> obtenerCita(@PathVariable Long id) {
        Optional<Cita> citaOptional = citaService.obtenerCita(id);
        return citaOptional.map(cita -> new ResponseEntity<>(new CitaDto(cita), HttpStatus.OK))
                           .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Actualizar una cita
    @PutMapping("/{id}")
    public ResponseEntity<Cita> actualizarCita(@PathVariable Long id, @RequestBody Cita cita) {
        Cita citaActualizada = citaService.actualizarCita(id, cita);
        return citaActualizada != null 
               ? new ResponseEntity<>(citaActualizada, HttpStatus.OK)
               : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Eliminar una cita
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCita(@PathVariable Long id) {
        boolean eliminado = citaService.eliminarCita(id);
        return eliminado ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                         : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
