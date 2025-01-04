package com.example.news_backend.service;

import com.example.news_backend.model.News;
import com.example.news_backend.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    public List<News> getAllNews() {
        return newsRepository.findAll();
    }
    public News addNews(News news) {
        return newsRepository.save(news);
    }

}
