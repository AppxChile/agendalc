package com.agendalc.agendalc.controllers;

import com.agendalc.agendalc.entities.BloqueHorario;
import com.agendalc.agendalc.services.BloqueHorarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/agendalc/bloques-horarios")
@CrossOrigin(origins = "https://dev.appx.cl/")
public class BloqueHorarioController {

    private final BloqueHorarioService bloqueHorarioService;

    public BloqueHorarioController(BloqueHorarioService bloqueHorarioService){
        this.bloqueHorarioService =bloqueHorarioService;
    }

    // Crear un nuevo bloque horario
    @PostMapping
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<BloqueHorario> crearBloqueHorario(@RequestBody BloqueHorario bloqueHorario) {
        BloqueHorario nuevoBloqueHorario = bloqueHorarioService.crearBloqueHorario(bloqueHorario);
        return new ResponseEntity<>(nuevoBloqueHorario, HttpStatus.CREATED);
    }

    // Obtener todos los bloques horarios
    @GetMapping
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<List<BloqueHorario>> obtenerTodosLosBloquesHorarios() {
        List<BloqueHorario> bloquesHorarios = bloqueHorarioService.obtenerTodosLosBloquesHorarios();
        return new ResponseEntity<>(bloquesHorarios, HttpStatus.OK);
    }

    // Obtener un bloque horario por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<BloqueHorario> obtenerBloqueHorarioPorId(@PathVariable Long id) {
        Optional<BloqueHorario> bloqueHorario = bloqueHorarioService.obtenerBloqueHorarioPorId(id);
        return bloqueHorario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualizar un bloque horario
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<BloqueHorario> actualizarBloqueHorario(@PathVariable Long id, @RequestBody BloqueHorario bloqueHorario) {
        BloqueHorario bloqueHorarioActualizado = bloqueHorarioService.actualizarBloqueHorario(id, bloqueHorario);
        return bloqueHorarioActualizado != null ? ResponseEntity.ok(bloqueHorarioActualizado) : ResponseEntity.notFound().build();
    }

    // Eliminar un bloque horario
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Void> eliminarBloqueHorario(@PathVariable Long id) {
        return bloqueHorarioService.eliminarBloqueHorario(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
