package com.example.sportstrackerapp.ui.news;

import com.example.sportstrackerapp.api.ApiClient;
import com.example.sportstrackerapp.api.ApiService;

import retrofit2.Call;
import retrofit2.Callback;

public class NewsRepository {
    private final ApiService apiService;

    public NewsRepository() {
        this.apiService = ApiClient.getApiService();
    }

    public void fetchNHLNews(Callback<NewsResponse> callback) {
        Call<NewsResponse> call = apiService.getNHLNews();
        call.enqueue(callback);  // Passes the callback to the activity or fragment
    }
}