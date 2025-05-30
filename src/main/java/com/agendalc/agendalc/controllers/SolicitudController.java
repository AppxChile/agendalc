package com.agendalc.agendalc.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.agendalc.agendalc.dto.DocumentosSubidosRequest;
import com.agendalc.agendalc.dto.SolicitudCitaResponse;
import com.agendalc.agendalc.dto.SolicitudRequest;
import com.agendalc.agendalc.dto.SolicitudResponse;
import com.agendalc.agendalc.dto.SolicitudResponseList;
import com.agendalc.agendalc.services.interfaces.SolicitudService;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.http.MediaType;

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

    @PreAuthorize("hasRole('FUNC')")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SolicitudResponse> crearSolicitudConDocumentos(
            @RequestParam("idTramite") Long idTramite,
            @RequestParam("rut") Integer rut,
            @RequestParam(value = "files", required = false) List<MultipartFile> files,
            @RequestParam(value = "idTipoDocumentos", required = false) List<Long> idTipoDocumentos) {
        // --- Streamlined Validations ---
        final boolean hasFiles = files != null && !files.isEmpty();
        final boolean hasIdTipoDocumentos = idTipoDocumentos != null && !idTipoDocumentos.isEmpty();

        if (hasFiles != hasIdTipoDocumentos) { // If one exists, the other must too
            if (hasFiles) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Se adjuntaron archivos pero no se especificaron los tipos de documentos asociados. Asegúrate de enviar 'idTipoDocumentos'.");
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Se especificaron tipos de documentos pero no se adjuntaron archivos. Asegúrate de enviar 'files'.");
            }
        }

        if (hasFiles && files.size() != idTipoDocumentos.size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La cantidad de archivos adjuntos (" + files.size()
                            + ") no coincide con la cantidad de IDs de tipos de documentos proporcionados ("
                            + idTipoDocumentos.size() + ").");
        }

        // --- Construction of SolicitudRequest for the service ---
        List<DocumentosSubidosRequest> documentosSubidosParaServicio = new ArrayList<>();
        // Only proceed if there are files (and thus, idTipoDocumentos)
        if (hasFiles) {
            for (int i = 0; i < files.size(); i++) {
                // Ensure neither file nor its corresponding ID is null before adding
                if (files.get(i) == null || idTipoDocumentos.get(i) == null) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Un archivo o su ID de tipo de documento asociado es nulo.");
                }
                documentosSubidosParaServicio.add(new DocumentosSubidosRequest(idTipoDocumentos.get(i), files.get(i)));
            }
        }

        SolicitudRequest request = new SolicitudRequest(idTramite, rut, documentosSubidosParaServicio);

        // --- Call to service and exception handling ---
        try {
            SolicitudResponse response = solicitudService.createSolicitud(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al procesar y guardar los documentos adjuntos: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ha ocurrido un error inesperado al crear la solicitud: " + e.getMessage(), e);
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
