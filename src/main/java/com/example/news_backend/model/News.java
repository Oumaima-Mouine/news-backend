package com.example.news_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;

@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(length = 50)
    private String category;

    @Size(max = 255, message = "Image URL cannot exceed 255 characters")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(length = 255)
    private String tags;

    @Column(nullable = false, updatable = false)
    private Timestamp publishDate;

    @Column(length = 50)
    private String readTime; // New field for read time

    // Enum for the status column
    public enum Status {
        PUBLISHED,
        DRAFT
    }

    // Constructor
    public News() {
        this.status = Status.DRAFT; // Default status
        this.publishDate = new Timestamp(System.currentTimeMillis()); // Default publishDate
        this.readTime = "5 min read"; // Default read time
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Timestamp getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }
}