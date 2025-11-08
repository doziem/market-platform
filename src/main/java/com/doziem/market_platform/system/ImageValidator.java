package com.doziem.market_platform.system;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

@Component
public class ImageValidator {
    private static final Set<String> ALLOWED_MIME_TYPES = Set.of(
            "image/png",
            "image/jpeg",
            "application/pdf",
            "application/msword", // .doc
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png");
    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2MB

    /**
     * Comprehensive image validation
     */
    public void validateImage(MultipartFile file) {
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }

        validateNotEmpty(file);
        validateFileSize(file);
        validateContentType(file);
        validateFileExtension(file);
        validateImageSignature(file);
    }

    /**
     * Quick validation for basic checks only
     */
    public void validateImageBasic(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Image file is required");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("Image size must be less than 2MB");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new IllegalArgumentException("File must be an image");
        }
    }

    private void validateNotEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Image file cannot be empty");
        }

        if (file.getOriginalFilename() == null || file.getOriginalFilename().trim().isEmpty()) {
            throw new IllegalArgumentException("Image file must have a name");
        }
    }

    private void validateFileSize(MultipartFile file) {
        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException(
                    String.format("Image size too large. Maximum allowed: %dMB, Actual: %.2fMB",
                            MAX_FILE_SIZE / (1024 * 1024),
                            (double) file.getSize() / (1024 * 1024))
            );
        }

        // Check if file is too small (might be corrupted)
        if (file.getSize() < 100) { // 100 bytes minimum
            throw new IllegalArgumentException("Image file appears to be corrupted or too small");
        }
    }

    private void validateContentType(MultipartFile file) {
        String contentType = file.getContentType();

        if (contentType == null) {
            throw new IllegalArgumentException("Could not determine image type");
        }

        if (!ALLOWED_MIME_TYPES.contains(contentType)) {
            throw new IllegalArgumentException(
                    String.format("Invalid image type: %s. Allowed types: JPEG, PNG", contentType)
            );
        }
    }

    private void validateFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) return;

        String extension = getFileExtension(originalFilename).toLowerCase();

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new IllegalArgumentException(
                    String.format("Invalid file extension: %s. Allowed: .jpg, .jpeg, .png", extension)
            );
        }
    }

    private void validateImageSignature(MultipartFile file) {
        try {
            byte[] fileBytes = file.getBytes();

            if (fileBytes.length < 8) {
                throw new IllegalArgumentException("Image file appears to be corrupted");
            }

            String contentType = file.getContentType();
            boolean validSignature = false;

            if ("image/jpeg".equals(contentType)) {
                validSignature = isJpeg(fileBytes);
            } else if ("image/png".equals(contentType)) {
                validSignature = isPng(fileBytes);
            }

            if (!validSignature) {
                throw new IllegalArgumentException(
                        "Image file signature doesn't match its type. File may be corrupted or mislabeled."
                );
            }

        } catch (IOException e) {
            throw new IllegalArgumentException("Error reading image file: " + e.getMessage());
        }
    }

    private boolean isJpeg(byte[] fileBytes) {
        // JPEG magic numbers: FF D8 FF
        return (fileBytes[0] & 0xFF) == 0xFF &&
                (fileBytes[1] & 0xFF) == 0xD8 &&
                (fileBytes[2] & 0xFF) == 0xFF;
    }

    private boolean isPng(byte[] fileBytes) {
        // PNG magic numbers: 89 50 4E 47 0D 0A 1A 0A
        return (fileBytes[0] & 0xFF) == 0x89 &&
                (fileBytes[1] & 0xFF) == 0x50 &&
                (fileBytes[2] & 0xFF) == 0x4E &&
                (fileBytes[3] & 0xFF) == 0x47 &&
                (fileBytes[4] & 0xFF) == 0x0D &&
                (fileBytes[5] & 0xFF) == 0x0A &&
                (fileBytes[6] & 0xFF) == 0x1A &&
                (fileBytes[7] & 0xFF) == 0x0A;
    }

    private String getFileExtension(String filename) {
        int lastDot = filename.lastIndexOf(".");
        if (lastDot == -1 || lastDot == filename.length() - 1) {
            return "";
        }
        return filename.substring(lastDot + 1);
    }

    // Utility methods
    public boolean isImage(MultipartFile file) {
        if (file == null || file.isEmpty()) return false;

        String contentType = file.getContentType();
        return contentType != null && contentType.startsWith("image/");
    }

    public Set<String> getAllowedMimeTypes() {
        return ALLOWED_MIME_TYPES;
    }

    public Set<String> getAllowedExtensions() {
        return ALLOWED_EXTENSIONS;
    }

    public long getMaxFileSize() {
        return MAX_FILE_SIZE;
    }
}
