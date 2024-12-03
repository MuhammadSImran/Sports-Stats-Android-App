package com.example.sportstrackerapp.ui.news;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// Initializes the room database where the articles are stored
@Database(entities = {ArticleEntity.class}, version = 1, exportSchema = false)
public abstract class ArticleDatabase extends RoomDatabase {
    private static volatile ArticleDatabase INSTANCE;

    public abstract ArticleDao articleDao();

    public static ArticleDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (ArticleDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ArticleDatabase.class,
                            "article_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}
