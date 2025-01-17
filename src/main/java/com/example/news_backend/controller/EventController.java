package com.example.news_backend.controller;

import com.example.news_backend.model.Event;
import com.example.news_backend.service.EventService;
import com.example.news_backend.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/event")
public class EventController  {
    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    private EventService eventService;

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public Event getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @PostMapping
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
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

    @PutMapping("/{id}")
    public Event updateEvent(
            @PathVariable Long id,
            @ModelAttribute Event event,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return eventService.updateEvent(id, event, image);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }
}