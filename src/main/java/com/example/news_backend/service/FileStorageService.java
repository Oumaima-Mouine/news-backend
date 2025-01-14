package com.example.news_backend.service;

import com.example.news_backend.config.FileStorageProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
public class FileStorageService {

    private final Path storagePath;

    public FileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.storagePath = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.storagePath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    public String saveFile(MultipartFile file) {
        try {
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            if (originalFileName == null) {
                throw new RuntimeException("Invalid file name!");
            }

            // Ensure the file name is unique
            String fileName = System.currentTimeMillis() + "_" + originalFileName;

            Path targetLocation = this.storagePath.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return fileName;
        } catch (IOException e) {
            throw new RuntimeException("Could not store file. Error: " + e.getMessage());
        }
    }


    public Path getFilePath(String fileName) {
        return this.storagePath.resolve(fileName).normalize();
    }
}
