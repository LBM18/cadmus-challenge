package com.project.cadmus_challenge.api.bases;

import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileStorageHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileStorageProperties.class);

    private static Path IMAGE_STORAGE_ABSOLUTE_PATH;

    public FileStorageHandler(FileStorageProperties fileStorageProperties) {
        var uploadDir = fileStorageProperties.getUploadDir();
        try {
            IMAGE_STORAGE_ABSOLUTE_PATH = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(IMAGE_STORAGE_ABSOLUTE_PATH);
            LOGGER.info("Directory " + uploadDir + " created or already exists at: " + IMAGE_STORAGE_ABSOLUTE_PATH);
        } catch (IOException ex) {
            LOGGER.error("Could not create " + uploadDir + " directory: " + ex.getMessage(), ex);
        }
    }

    public static void uploadImage(@NotNull MultipartFile file, @NotNull String imageUniqueIdentifier) {
        var fileName = getFileFinalName(getFileNameFromMultipartFile(file), imageUniqueIdentifier);
        try {
            var filePath = getFilePathFromFileName(fileName);
            file.transferTo(filePath);
            LOGGER.info("Image uploaded successfully: " + fileName);
        } catch (IOException ex) {
            LOGGER.error("Error uploading image: " + ex.getMessage(), ex);
        }
    }

    public static Resource getImage(@NotNull String fileName, @NotNull String imageUniqueIdentifier) {
        var filePath = getFilePathFromFileName(getFileFinalName(fileName, imageUniqueIdentifier));
        try {
            var resource = new UrlResource(filePath.toUri());
            LOGGER.info("Image uploaded successfully: " + fileName);
            return resource;
        } catch (IOException ex) {
            LOGGER.error("Error retrieving image: " + ex.getMessage(), ex);
            return null;
        }
    }

    public static void deleteImage(@NotNull String fileName, @NotNull String imageUniqueIdentifier) {
        var filePath = getFilePathFromFileName(getFileFinalName(fileName, imageUniqueIdentifier));
        try {
            Files.deleteIfExists(filePath);
            LOGGER.info("Image deleted successfully: " + fileName);
        } catch (IOException ex) {
            LOGGER.error("Error deleting image: " + ex.getMessage(), ex);
        }
    }

    public static String getFileNameFromMultipartFile(@NotNull MultipartFile file) {
        return StringUtils.cleanPath(file.getOriginalFilename()).trim();
    }

    private static String getFileFinalName(@NotNull String fileName, @NotNull String imageUniqueIdentifier) {
        return imageUniqueIdentifier.trim() + "_" + fileName.trim();
    }

    private static Path getFilePathFromFileName(@NotNull String fileName) {
        return IMAGE_STORAGE_ABSOLUTE_PATH.resolve(fileName.trim()).normalize();
    }
}
