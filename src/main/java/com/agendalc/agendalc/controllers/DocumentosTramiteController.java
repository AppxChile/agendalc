package com.agendalc.agendalc.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agendalc.agendalc.dto.DocumentosTramiteRequest;
import com.agendalc.agendalc.dto.DocumentosTramiteResponse;
import com.agendalc.agendalc.services.interfaces.DocumentosTramiteService;

@RestController
@RequestMapping("/api/agendalc/documentos-tramite")
public class DocumentosTramiteController {

    private final DocumentosTramiteService documentosTramiteService;

    public DocumentosTramiteController(DocumentosTramiteService documentosTramiteService) {
        this.documentosTramiteService = documentosTramiteService;
    }

    @PostMapping
    @PreAuthorize("hasRole('FUNC')")
    public ResponseEntity<Object> createDocuementosTramite(@RequestBody DocumentosTramiteRequest request) {
        try {
            DocumentosTramiteResponse response = documentosTramiteService.createDocumentosTramite(request);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}