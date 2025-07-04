package com.agendalc.agendalc.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.agendalc.agendalc.config.AppProperties;
import com.agendalc.agendalc.services.interfaces.ArchivoService;

@Service
public class ArchivoServiceImpl implements ArchivoService {

    private final String uploadDir;

    public ArchivoServiceImpl(AppProperties appProperties) {
        this.uploadDir = appProperties.getUploadDir();
    }

    @Override
    public String guardarArchivo(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);

        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        Path filePath = uploadPath.resolve(uniqueFileName);

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }

    @Override
    public Path getRutaCompletaArchivo(String nombreGuardado) throws IOException {
        return Paths.get(uploadDir).toAbsolutePath().normalize().resolve(nombreGuardado);
    }

}
