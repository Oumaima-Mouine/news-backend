package com.example.news_backend.controller;

import com.example.news_backend.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/api/search")
    public ResponseEntity<?> search(@RequestParam String query) {
        return ResponseEntity.ok(searchService.searchAll(query));
    }
}
