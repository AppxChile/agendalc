package com.agendalc.agendalc.controllers;

import com.agendalc.agendalc.entities.BloqueHorario;
import com.agendalc.agendalc.services.interfaces.BloqueHorarioService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agendalc/bloques-horarios")
@CrossOrigin(origins = "https://dev.appx.cl/")
public class BloqueHorarioController {

    private final BloqueHorarioService bloqueHorarioService;

    public BloqueHorarioController(BloqueHorarioService bloqueHorarioService) {
        this.bloqueHorarioService = bloqueHorarioService;
    }

    // Crear un nuevo bloque horario
    @PostMapping
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<BloqueHorario> createBloqueHorario(@RequestBody BloqueHorario bloqueHorario) {
        BloqueHorario nuevoBloqueHorario = bloqueHorarioService.createBloqueHorario(bloqueHorario);
        return new ResponseEntity<>(nuevoBloqueHorario, HttpStatus.CREATED);
    }

    // Obtener todos los bloques horarios
    @GetMapping
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<List<BloqueHorario>> getAllBloquesHorarios() {
        List<BloqueHorario> bloquesHorarios = bloqueHorarioService.getAllBloquesHorarios();
        return new ResponseEntity<>(bloquesHorarios, HttpStatus.OK);
    }

    // Obtener un bloque horario por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> getBloqueHorarioById(@PathVariable Long id) {
        BloqueHorario bloqueHorario = bloqueHorarioService.getBloqueHorarioById(id);
        try {
            return ResponseEntity.ok(bloqueHorario);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Actualizar un bloque horario
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<BloqueHorario> updateBloqueHorario(@PathVariable Long id,
            @RequestBody BloqueHorario bloqueHorario) {
        BloqueHorario bloqueHorarioActualizado = bloqueHorarioService.updateBloqueHorario(id, bloqueHorario);
        return bloqueHorarioActualizado != null ? ResponseEntity.ok(bloqueHorarioActualizado)
                : ResponseEntity.notFound().build();
    }

    // Eliminar un bloque horario
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<String> deleteBloqueHorario(@PathVariable Long id) {
        try {
            bloqueHorarioService.deleteBloqueHorarioById(id);
            return ResponseEntity.ok("Registro eliminado correctamente");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar: " + e.getMessage());
        }
    }

}
