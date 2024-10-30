package com.example.sportstrackerapp.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;
import com.example.sportstrackerapp.ApiClient;

public class NewsViewModel extends ViewModel {
    private final NewsRepository newsRepository;
    private LiveData<List<Article>> newsArticles;

    public NewsViewModel() {
        // Initialize NewsRepository with an instance of NewsApiService
        NewsApiService apiService = ApiClient.getRetrofit().create(NewsApiService.class);
        newsRepository = new NewsRepository(apiService);
    }

    public LiveData<List<Article>> getNewsArticles(String query) {
        if (newsArticles == null) {
            newsArticles = newsRepository.getNewsArticles(query);
        }
        return newsArticles;
    }
}
