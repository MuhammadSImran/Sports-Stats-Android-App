package com.example.sportstrackerapp.ui.news;

import java.util.ArrayList;
import java.util.List;

// Mapper to convert Article.java to ArticleEntity.java
public class ArticleMapper {
    public static List<Article> mapToDomainList(List<ArticleEntity> articleEntities) {
        List<Article> articles = new ArrayList<>();
        for (ArticleEntity entity : articleEntities) {
            Article article = new Article();
            article.setTitle(entity.getTitle());
            article.setDescription(entity.getDescription());
            article.setUrl(entity.getUrl());
            article.setUrlToImage(entity.getUrlToImage());
            articles.add(article);
        }
        return articles;
    }

    // Convert a list of Article objects to a list of ArticleEntity objects
    public static List<ArticleEntity> mapToEntityList(List<Article> articles) {
        List<ArticleEntity> articleEntities = new ArrayList<>();
        for (Article article : articles) {
            ArticleEntity entity = new ArticleEntity();
            entity.setTitle(article.getTitle());
            entity.setDescription(article.getDescription());
            entity.setUrl(article.getUrl());
            entity.setUrlToImage(article.getUrlToImage());
            articleEntities.add(entity);
        }
        return articleEntities;
    }
}
