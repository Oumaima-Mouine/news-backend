package com.example.news_backend.service;

import com.example.news_backend.model.News;
import com.example.news_backend.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public News updateNews(Long id, News updatedNews) {
        try {
            News existingNews = getNewsById(id);

            if (updatedNews.getContent() == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Content field cannot be null");
            }

            existingNews.setTitle(updatedNews.getTitle());
            existingNews.setContent(updatedNews.getContent());
            existingNews.setCategory(updatedNews.getCategory());
            existingNews.setImageUrl(updatedNews.getImageUrl());
            existingNews.setStatus(updatedNews.getStatus());
            existingNews.setTags(updatedNews.getTags());
            existingNews.setPublishDate(updatedNews.getPublishDate()); // Allow updating the publishing date

            return newsRepository.save(existingNews);
        } catch (Exception e) {
            // Log the error for debugging
            System.err.println("Error updating news: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating news", e);
        }
    }



    // Delete a news item
    public void deleteNews(Long id) {
        if (!newsRepository.existsById(id)) {
            throw new RuntimeException("News not found with ID: " + id);
        }
        newsRepository.deleteById(id);
    }
}
