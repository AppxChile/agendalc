package com.agendalc.agendalc.services.interfaces;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

public interface ArchivoService {

    String guardarArchivo(MultipartFile file) throws IOException;

    Path getRutaCompletaArchivo(String nombreGuardado) throws IOException;

}
