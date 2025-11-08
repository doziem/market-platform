package com.doziem.market_platform.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService() {
        this.fileStorageLocation = Paths.get("uploads/store-logos")
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory", e);
        }
    }

    /**
     * Stores file in filesystem and returns the filename
     */
    public String storeFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        // Generate unique filename
        String originalFilename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileExtension = getFileExtension(originalFilename);
        String filename = UUID.randomUUID() + "." + fileExtension;

        // Validate filename
        if (filename.contains("..")) {
            throw new IllegalArgumentException("Filename contains invalid path sequence: " + filename);
        }

        // Copy file to target location
        Path targetLocation = this.fileStorageLocation.resolve(filename);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return filename;
    }

    /**
     * Gets file path for serving
     */
    public Path getFilePath(String filename) {
        return fileStorageLocation.resolve(filename).normalize();
    }

    /**
     * Deletes file from filesystem
     */
    public void deleteFile(String filename) throws IOException {
        if (filename != null) {
            Path filePath = getFilePath(filename);
            Files.deleteIfExists(filePath);
        }
    }

    /**
     * Checks if file exists
     */
    public boolean fileExists(String filename) {
        if (filename == null) return false;
        return Files.exists(getFilePath(filename));
    }

    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return "bin"; // default extension
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
