package com.example.news_backend.repository;

import com.example.news_backend.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String title, String content);
}
