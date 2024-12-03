package com.example.sportstrackerapp.ui.news;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {
    private final NewsRepository newsRepository;

    public NewsViewModel(@NonNull Application application) {
        super(application);
        NewsApiService apiService = ApiClient.getRetrofit().create(NewsApiService.class);
        ArticleDatabase database = ArticleDatabase.getInstance(application.getApplicationContext());
        newsRepository = new NewsRepository(apiService, database);
    }

    public LiveData<List<ArticleEntity>> getNewsArticles(String query, Context context) {
        return newsRepository.getNewsArticles(query, getApplication());
    }
}
