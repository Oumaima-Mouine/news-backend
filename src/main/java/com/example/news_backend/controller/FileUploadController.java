//package com.example.news_backend.controller;
//
//import com.example.news_backend.service.FileStorageService;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.server.ResponseStatusException;
//
//@RestController
//@RequestMapping("/api/news/files")
//public class FileUploadController {
//
//    private final FileStorageService fileStorageService;
//
//    public FileUploadController(FileStorageService fileStorageService) {
//        this.fileStorageService = fileStorageService;
//    }
//
//    @PostMapping("/upload/")
//    public String handleImageUpload(@RequestParam("file") MultipartFile file) {
//        try {
//            System.out.println("File received: " + file.getOriginalFilename()); // Log file name
//            String imageUrl = "/uploads/" + fileStorageService.storeFile(file);
//            return "http://localhost:8081" + imageUrl;
//        } catch (Exception e) {
//            e.printStackTrace(); // Log the error
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to upload file", e);
//        }
//    }
//}
