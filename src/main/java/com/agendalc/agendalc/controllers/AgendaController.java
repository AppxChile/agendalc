package com.agendalc.agendalc.controllers;

import com.agendalc.agendalc.dto.AgendaRequest;
import com.agendalc.agendalc.entities.Agenda;
import com.agendalc.agendalc.services.AgendaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/agendas")
public class AgendaController {

    
    private final AgendaService agendaService;

    public AgendaController(AgendaService agendaService){
        this.agendaService = agendaService;
    }

    // Crear una nueva agenda
    @PostMapping
    public ResponseEntity<Agenda> crearAgenda(@RequestBody AgendaRequest request) {
        Agenda nuevaAgenda = agendaService.crearAgenda(request);
        return new ResponseEntity<>(nuevaAgenda, HttpStatus.CREATED);
    }
    // Obtener todas las agendas
    @GetMapping
    public ResponseEntity<List<Agenda>> obtenerTodasLasAgendas() {
        List<Agenda> agendas = agendaService.obtenerTodasLasAgendas();
        return new ResponseEntity<>(agendas, HttpStatus.OK);
    }

    // Obtener una agenda por ID
    @GetMapping("/{id}")
    public ResponseEntity<Agenda> obtenerAgendaPorId(@PathVariable Long id) {
        Optional<Agenda> agenda = agendaService.obtenerAgendaPorId(id);
        return agenda.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Actualizar una agenda
    @PutMapping("/{id}")
    public ResponseEntity<Agenda> actualizarAgenda(@PathVariable Long id, @RequestBody Agenda agenda) {
        Agenda agendaActualizada = agendaService.actualizarAgenda(id, agenda);
        return agendaActualizada != null ? ResponseEntity.ok(agendaActualizada) : ResponseEntity.notFound().build();
    }

    // Eliminar una agenda
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAgenda(@PathVariable Long id) {
        return agendaService.eliminarAgenda(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
