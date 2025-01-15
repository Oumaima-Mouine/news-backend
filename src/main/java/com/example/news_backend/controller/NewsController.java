package com.example.news_backend.controller;

import com.example.news_backend.model.News;
import com.example.news_backend.service.NewsService;
import com.example.news_backend.service.FileStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private FileStorageService fileStorageService; // Correctly inject the service

    // Get all news items
    @GetMapping
    public List<News> getAllNews() {
        return newsService.getAllNews();
    }

    // Get a specific news item by ID
    @GetMapping("/{id}")
    public News getNewsById(@PathVariable Long id) {
        return newsService.getNewsById(id);
    }

    // Create a new news item
    @PostMapping
    public News createNews(@RequestBody News news) {
        return newsService.createNews(news);
    }

    // Update a news item
    @PutMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<News> updateNews(
            @PathVariable Long id,
            @RequestParam("title") String title,
            @RequestParam("author") String author,
            @RequestParam("date") String date,
            @RequestParam("category") String category,
            @RequestParam("description") String description, // Correct name for description/content
            @RequestParam("tags") String tags,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        // Parse the date string into a Timestamp
        Timestamp publishDate = Timestamp.valueOf(date);

        News updatedNews = new News();
        updatedNews.setTitle(title);
        updatedNews.setPublishDate(publishDate);
        updatedNews.setCategory(category);
        updatedNews.setContent(description); // Set the content using description parameter
        updatedNews.setTags(tags);

        if (file != null) {
            // Add your logic here to save the file and get the URL
            // e.g., updatedNews.setImageUrl(uploadImage(file));
        }

        News updatedNewsResult = newsService.updateNews(id, updatedNews);
        return new ResponseEntity<>(updatedNewsResult, HttpStatus.OK);
    }

//    public News updateNews(@RequestBody News news, @PathVariable Long id) {
//        return newsService.updateNews(id, news);
//    }

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
