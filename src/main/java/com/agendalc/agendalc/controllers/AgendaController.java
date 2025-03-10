package com.agendalc.agendalc.controllers;

import com.agendalc.agendalc.dto.AgendaRequest;
import com.agendalc.agendalc.entities.Agenda;
import com.agendalc.agendalc.entities.BloqueHorario;
import com.agendalc.agendalc.services.AgendaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/agendalc/agendas")
@CrossOrigin(origins = "https://dev.appx.cl/")
public class AgendaController {

    private final AgendaService agendaService;

    public AgendaController(AgendaService agendaService) {
        this.agendaService = agendaService;
    }

    // Crear una nueva agenda
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Agenda> crearAgenda(@RequestBody AgendaRequest request) {
        Agenda nuevaAgenda = agendaService.crearAgenda(request);
        return new ResponseEntity<>(nuevaAgenda, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<List<Agenda>> obtenerTodasLasAgendas() {
        List<Agenda> agendas = agendaService.obtenerTodasLasAgendas();
        return new ResponseEntity<>(agendas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Agenda> obtenerAgendaPorId(@PathVariable Long id) {
        Optional<Agenda> agenda = agendaService.obtenerAgendaPorId(id);
        return agenda.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Agenda> actualizarAgenda(@PathVariable Long id, @RequestBody Agenda agenda) {
        Agenda agendaActualizada = agendaService.actualizarAgenda(id, agenda);
        return agendaActualizada != null ? ResponseEntity.ok(agendaActualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Void> eliminarAgenda(@PathVariable Long id) {
        return agendaService.eliminarAgenda(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/{idAgenda}/agregarBloques")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Agenda> agregarBloquesAHorario(
            @PathVariable Long idAgenda,
            @RequestBody List<BloqueHorario> bloquesHorarios) {

        Agenda agenda = agendaService.agregarBloquesAHorario(idAgenda, bloquesHorarios);

        return ResponseEntity.ok(agenda);
    }

    @DeleteMapping("/{idAgenda}/eliminarBloque/{idBloqueHorario}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Agenda> eliminarBloqueDeAgenda(
            @PathVariable Long idAgenda,
            @PathVariable Long idBloqueHorario) {

        try {
            Agenda agenda = agendaService.eliminarBloqueDeAgenda(idAgenda, idBloqueHorario);
            return ResponseEntity.ok(agenda);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

    @PutMapping("/{idAgenda}/actualizarBloques")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Agenda> actualizarBloquesDeAgenda(
            @PathVariable Long idAgenda,
            @RequestBody List<BloqueHorario> bloquesHorarioActualizados) {

        try {
            Agenda agenda = agendaService.actualizarBloquesHorariosDeAgenda(idAgenda, bloquesHorarioActualizados);
            return ResponseEntity.ok(agenda);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}
