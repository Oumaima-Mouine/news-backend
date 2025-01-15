package com.example.news_backend.model;

import jakarta.persistence.*;
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String date; // Change to String

    @Column(nullable = false)
    private String time; // Change to String

    @Column(length = 255)
    private String location;

    @Column(length = 100)
    private String category;

    @Column(nullable = false, length = 100)
    private int capacity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('UPCOMING', 'LIMITED_SEATS', 'OPEN') DEFAULT 'UPCOMING'")
    private Status status = Status.UPCOMING;

    @Column(length = 255)
    private String imageUrl;

    // Enum for the status column
    public enum Status {
        UPCOMING,
        LIMITED_SEATS,
        OPEN
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}