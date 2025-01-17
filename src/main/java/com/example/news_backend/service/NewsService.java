package com.example.news_backend.service;

import com.example.news_backend.model.News;
import com.example.news_backend.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private FileStorageService fileStorageService;

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
            news.setPublishDate(new Timestamp(System.currentTimeMillis())); // Set default publish date
        }
        if (news.getReadTime() == null) {
            news.setReadTime("5 min read"); // Set default read time
        }
        if (news.getAuthor() == null) {
            news.setAuthor("Anonymous"); // Set default author
        }
        return newsRepository.save(news);
    }

    // Update a news item
    public News updateNews(Long id, News updatedNews, MultipartFile image) {
        try {
            News existingNews = getNewsById(id);

            // Update fields
            existingNews.setTitle(updatedNews.getTitle());
            existingNews.setContent(updatedNews.getContent());
            existingNews.setCategory(updatedNews.getCategory());
            existingNews.setTags(updatedNews.getTags());
            existingNews.setStatus(updatedNews.getStatus());
            existingNews.setAuthor(updatedNews.getAuthor()); // Update author

            // Handle image upload
            if (image != null && !image.isEmpty()) {
                String fileName = fileStorageService.saveFile(image);
                existingNews.setImageUrl("/uploads/" + fileName); // Update the image URL
            }

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

    // Format publishDate as a String
    public String formatPublishDate(Timestamp publishDate) {
        return new SimpleDateFormat("MMMM dd, yyyy").format(publishDate);
    }
}