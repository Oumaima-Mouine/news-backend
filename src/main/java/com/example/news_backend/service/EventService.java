package com.example.news_backend.service;

import com.example.news_backend.model.Event;
import com.example.news_backend.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private final EventRepository eventRepository;

    @Autowired
    private FileStorageService fileStorageService; // Inject the file storage service

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + id));

    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Long id, Event updateEvent, MultipartFile image) {
        Event existingEvent = getEventById(id);

        // Update the event fields
        existingEvent.setTitle(updateEvent.getTitle());
        existingEvent.setDescription(updateEvent.getDescription());
        existingEvent.setDate(updateEvent.getDate());
        existingEvent.setTime(updateEvent.getTime());
        existingEvent.setLocation(updateEvent.getLocation());
        existingEvent.setCategory(updateEvent.getCategory());
        existingEvent.setStatus(updateEvent.getStatus());
        existingEvent.setCapacity(updateEvent.getCapacity());

        // Handle the image upload
        if (image != null && !image.isEmpty()) {
            String fileName = fileStorageService.saveFile(image);
            existingEvent.setImageUrl("/uploads/" + fileName); // Update the image URL
        }

        return eventRepository.save(existingEvent);
    }
    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new RuntimeException("Event not found with ID: " + id);
        }
        eventRepository.deleteById(id);
    }


}
