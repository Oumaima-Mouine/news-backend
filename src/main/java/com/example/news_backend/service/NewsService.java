package com.example.news_backend.service;

import com.example.news_backend.model.News;
import com.example.news_backend.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    // Get all news items
    public List<News> getAllNews() {
        return newsRepository.findAll();
    }

    // Get a specific news item by ID
    public News getNewsById(Long id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("News not found with ID: " + id));
    }

    // Create a new news item
    public News createNews(News news) {
        if (news.getPublishDate() == null) {
            throw new IllegalArgumentException("Publish date cannot be null."); // Ensure publish date is provided
        }
        return newsRepository.save(news);
    }

    // Update an existing news item
    public News updateNews(Long id, News updatedNews) {
        News existingNews = getNewsById(id);

        existingNews.setTitle(updatedNews.getTitle());
        existingNews.setContent(updatedNews.getContent());
        existingNews.setCategory(updatedNews.getCategory());
        existingNews.setImageUrl(updatedNews.getImageUrl());
        existingNews.setStatus(updatedNews.getStatus());
        existingNews.setTags(updatedNews.getTags());
        existingNews.setPublishDate(updatedNews.getPublishDate()); // Allow updating the publishing date

        return newsRepository.save(existingNews);
    }

    // Delete a news item
    public void deleteNews(Long id) {
        if (!newsRepository.existsById(id)) {
            throw new RuntimeException("News not found with ID: " + id);
        }
        newsRepository.deleteById(id);
    }
}
