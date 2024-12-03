package com.example.sportstrackerapp.ui.news;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// An article in the Room database
@Entity(tableName = "articles")
public class ArticleEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String description;
    private String url;
    private String urlToImage;

    // Getters/setters
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getUrl() { return url; }

    public void setUrl(String url) { this.url = url; }

    public String getUrlToImage() { return urlToImage; }

    public void setUrlToImage(String urlToImage) { this.urlToImage = urlToImage; }
}
