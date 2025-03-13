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
    public ResponseEntity<Agenda> createAgenda(@RequestBody AgendaRequest request) {
        Agenda nuevaAgenda = agendaService.createAgenda(request);
        return new ResponseEntity<>(nuevaAgenda, HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<List<Agenda>> getAllAgendas() {
        List<Agenda> agendas = agendaService.getAllAgendas();
        return new ResponseEntity<>(agendas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Agenda> getAgendaById(@PathVariable Long id) {
        Optional<Agenda> agenda = agendaService.getAgendaById(id);
        return agenda.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Agenda> updateAgenda(@PathVariable Long id, @RequestBody Agenda agenda) {
        Agenda agendaActualizada = agendaService.updateAgenda(id, agenda);
        return agendaActualizada != null ? ResponseEntity.ok(agendaActualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Void> deleteAgenda(@PathVariable Long id) {
        return agendaService.deleteAgendaById(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PostMapping("/{idAgenda}/agregarBloques")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Agenda> addBloquesAHorario(
            @PathVariable Long idAgenda,
            @RequestBody List<BloqueHorario> bloquesHorarios) {

        Agenda agenda = agendaService.addBloquesAHorario(idAgenda, bloquesHorarios);

        return ResponseEntity.ok(agenda);
    }

    @DeleteMapping("/{idAgenda}/eliminarBloque/{idBloqueHorario}")
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Agenda> deleteBloqueDeAgenda(
            @PathVariable Long idAgenda,
            @PathVariable Long idBloqueHorario) {

        try {
            Agenda agenda = agendaService.deleteBloqueDeAgenda(idAgenda, idBloqueHorario);
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
    public ResponseEntity<Agenda> updateBloquesDeAgenda(
            @PathVariable Long idAgenda,
            @RequestBody List<BloqueHorario> bloquesHorarioActualizados) {

        try {
            Agenda agenda = agendaService.updateBloquesHorariosDeAgenda(idAgenda, bloquesHorarioActualizados);
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
