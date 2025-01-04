package com.example.news_backend.model;

import jakarta.persistence.*;


@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_news;

    @Column(nullable = false)
    private String title_news ;

    @Column
    private String content_news ;

    @Column
    private String picture_news ;

    @Column
    private String time_news ;

    @Column
    private String location_news ;

    public long getId_news() {
        return id_news;
    }

    public void setId_news(long id_news) {
        this.id_news = id_news;
    }

    public String getTitle_news() {
        return title_news;
    }

    public void setTitle_news(String title_news) {
        this.title_news = title_news;
    }

    public String getContent_news() {
        return content_news;
    }

    public void setContent_news(String content_news) {
        this.content_news = content_news;
    }

    public String getPicture_news() {
        return picture_news;
    }

    public void setPicture_news(String picture_news) {
        this.picture_news = picture_news;
    }

    public String getTime_news() {
        return time_news;
    }

    public void setTime_news(String time_news) {
        this.time_news = time_news;
    }

    public String getLocation_news() {
        return location_news;
    }

    public void setLocation_news(String location_news) {
        this.location_news = location_news;
    }
}
