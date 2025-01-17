package com.example.news_backend.controller;

import com.example.news_backend.model.News;
import com.example.news_backend.service.NewsService;
import com.example.news_backend.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private FileStorageService fileStorageService;

    // Get all news items
    @GetMapping
    public List<News> getAllNews() {
        return newsService.getAllNews();
    }

    // Get a specific news item by ID
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getNewsById(@PathVariable Long id) {
        News news = newsService.getNewsById(id);

        // Format the publishDate as a String
        String formattedDate = newsService.formatPublishDate(news.getPublishDate());

        // Create a response object
        Map<String, Object> response = new HashMap<>();
        response.put("id", news.getId());
        response.put("title", news.getTitle());
        response.put("content", news.getContent());
        response.put("category", news.getCategory());
        response.put("imageUrl", news.getImageUrl());
        response.put("date", formattedDate); // Formatted date
        response.put("readTime", news.getReadTime()); // Read time
        response.put("tags", news.getTags());

        return ResponseEntity.ok(response);
    }

    // Create a new news item
    @PostMapping
    public News createNews(@RequestBody News news) {
        return newsService.createNews(news);
    }

    // Update a news item
    @PutMapping("/{id}")
    public ResponseEntity<News> updateNews(
            @PathVariable Long id,
            @RequestPart("news") News updatedNews,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        News updatedNewsResult = newsService.updateNews(id, updatedNews, image);
        return new ResponseEntity<>(updatedNewsResult, HttpStatus.OK);
    }

    // Delete a news item
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
        return ResponseEntity.ok().build();
    }

    // Handle the image uploads
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> handleImageUpload(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = fileStorageService.saveFile(file);
            Map<String, String> response = new HashMap<>();
            response.put("imageUrl", "/uploads/" + fileName); // Adjust the URL based on your application's needs
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Serve uploaded files
    @GetMapping("/file/{fileName}")
    public ResponseEntity<Object> getFile(@PathVariable String fileName) {
        try {
            Path filePath = fileStorageService.getFilePath(fileName);
            if (!Files.exists(filePath)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found!");
            }
            return ResponseEntity.ok().body(Files.readAllBytes(filePath));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}