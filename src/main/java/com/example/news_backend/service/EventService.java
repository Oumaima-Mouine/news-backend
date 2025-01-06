package com.example.news_backend.service;

import com.example.news_backend.model.Event;
import com.example.news_backend.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    @Autowired
    private final EventRepository eventRepository;

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

    public Event updateEvent(Long id, Event updateEvent) {
        Event newEvent = getEventById(id);
        newEvent.setTitle(updateEvent.getTitle());
        newEvent.setDescription(updateEvent.getDescription());
        newEvent.setDate(updateEvent.getDate());
        newEvent.setTime(updateEvent.getTime());
        newEvent.setLocation(updateEvent.getLocation());
        newEvent.setCategory(updateEvent.getCategory());
        newEvent.setStatus(updateEvent.getStatus());
        newEvent.setImageUrl(updateEvent.getImageUrl());
        newEvent.setAttendees(updateEvent.getAttendees());

        return eventRepository.save(newEvent);
    }

    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new RuntimeException("Event not found with ID: " + id);
        }
        eventRepository.deleteById(id);
    }


}
