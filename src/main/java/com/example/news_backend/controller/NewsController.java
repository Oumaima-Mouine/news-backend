package com.example.news_backend.controller;

import com.example.news_backend.model.News;
import com.example.news_backend.repository.NewsRepository;
import com.example.news_backend.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    @Autowired
    private NewsService newsService;

    @GetMapping
    public List<News> getAllNews() {
        return newsService.getAllNews();
    }

    @PostMapping
    public News addNews(@RequestBody News news) {
        return newsService.addNews(news);
    }
}
