package com.example.sportstrackerapp.ui.news;

public class Article {
    private String headline;
    private String description;
    private String published;
    private String link;

    // Getters
    public String getHeadline() { return headline; }
    public String getDescription() { return description; }
    public String getPublished() { return published; }
    public String getLink() { return link; }

    // Setters
    public void setHeadline(String headline) { this.headline = headline; }
    public void setDescription(String description) { this.description = description; }
    public void setPublished(String published) { this.published = published; }
    public void setLink(String link) { this.link = link; }
}
