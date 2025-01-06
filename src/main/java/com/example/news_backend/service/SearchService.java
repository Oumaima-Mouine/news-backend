package com.example.news_backend.service;

import com.example.news_backend.dto.SearchResult;
import com.example.news_backend.model.Event;
import com.example.news_backend.model.News;
import com.example.news_backend.repository.EventRepository;
import com.example.news_backend.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private EventRepository eventRepository;


    public List<SearchResult> searchAll(String query) {
        List<SearchResult> results = new ArrayList<>();

        // Search events
        List<Event> events = eventRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);
        for (Event event : events) {
            results.add(new SearchResult("event", event.getId(), event.getTitle(), event.getDescription(), event.getCategory(), event.getImageUrl()));
        }

        // Search news
        List<News> newsList = newsRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(query, query);
        for (News news : newsList) {
            results.add(new SearchResult("news", news.getId(), news.getTitle(), news.getContent(), news.getCategory(), news.getImageUrl()));
        }

        return results;
    }
}
