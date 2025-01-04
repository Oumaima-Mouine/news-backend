package com.example.news_backend.model;

import jakarta.persistence.*;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_activity;

    @Column(nullable = false)
    private String title_activity ;

    @Column
    private String content_activity ;

    @Column
    private String picture_activity ;

    @Column
    private String time_activity ;

    @Column
    private String location_activity ;

    public long getId_activity() {
        return id_activity;
    }

    public void setId_activity(long id_activity) {
        this.id_activity = id_activity;
    }

    public String getTitle_activity() {
        return title_activity;
    }

    public void setTitle_activity(String title_activity) {
        this.title_activity = title_activity;
    }

    public String getContent_activity() {
        return content_activity;
    }

    public void setContent_activity(String content_activity) {
        this.content_activity = content_activity;
    }

    public String getPicture_activity() {
        return picture_activity;
    }

    public void setPicture_activity(String picture_activity) {
        this.picture_activity = picture_activity;
    }

    public String getTime_activity() {
        return time_activity;
    }

    public void setTime_activity(String time_activity) {
        this.time_activity = time_activity;
    }

    public String getLocation_activity() {
        return location_activity;
    }

    public void setLocation_activity(String location_activity) {
        this.location_activity = location_activity;
    }
}
