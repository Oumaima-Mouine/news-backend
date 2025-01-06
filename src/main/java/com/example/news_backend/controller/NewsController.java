package com.example.news_backend.controller;

import com.example.news_backend.model.News;
import com.example.news_backend.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

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
    public News updateNews(@RequestBody News news, @PathVariable Long id) {
        return newsService.updateNews(id, news);
    }

    // Delete a news item
    @DeleteMapping("/{id}")
    public void deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
    }
}
