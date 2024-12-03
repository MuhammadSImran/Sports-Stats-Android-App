package com.example.sportstrackerapp.ui.news;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

//  Methods to interact with the database
@Dao
public interface ArticleDao {
    @Query("SELECT * FROM articles")
    LiveData<List<ArticleEntity>> getAllArticles();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticles(List<ArticleEntity> articles);

    @Query("DELETE FROM articles")
    void clearArticles();
}
