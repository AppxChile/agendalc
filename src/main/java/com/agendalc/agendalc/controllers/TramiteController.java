package com.agendalc.agendalc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agendalc.agendalc.entities.Tramite;
import com.agendalc.agendalc.services.TramiteService;

@RestController
@RequestMapping("/api/tramites")
@CrossOrigin(origins = "http://localhost:5173")
public class TramiteController {

    private final TramiteService tramiteService;

    public TramiteController(TramiteService tramiteService) {
        this.tramiteService = tramiteService;
    }

    @PostMapping
    public ResponseEntity<Tramite> crearTramite(@RequestBody Tramite tramite) {
        Tramite nuevoTramite = tramiteService.crearTramite(tramite);
        return new ResponseEntity<>(nuevoTramite, HttpStatus.CREATED);
    }

    // Obtener todos los trámites
    @GetMapping
    public ResponseEntity<List<Tramite>> obtenerTodosLosTramites() {
        List<Tramite> tramites = tramiteService.obtenerTodosLosTramites();
        return new ResponseEntity<>(tramites, HttpStatus.OK);
    }

    // Obtener un trámite por ID
    @GetMapping("/{id}")
    public ResponseEntity<Tramite> obtenerTramitePorId(@PathVariable Long id) {
        Optional<Tramite> tramite = tramiteService.obtenerTramitePorId(id);
        return tramite.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Actualizar un trámite
    @PutMapping("/{id}")
    public ResponseEntity<Tramite> actualizarTramite(@PathVariable Long id, @RequestBody Tramite tramite) {
        Tramite tramiteActualizado = tramiteService.actualizarTramite(id, tramite);
        return tramiteActualizado != null ? new ResponseEntity<>(tramiteActualizado, HttpStatus.OK)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Eliminar un trámite
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTramite(@PathVariable Long id) {
        boolean eliminado = tramiteService.eliminarTramite(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
