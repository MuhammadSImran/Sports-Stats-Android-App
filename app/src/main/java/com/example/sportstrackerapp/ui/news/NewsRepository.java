package com.example.sportstrackerapp.ui.news;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private final NewsApiService apiService;

    public NewsRepository(NewsApiService apiService) {
        this.apiService = apiService;
    }

    public LiveData<List<Article>> getNewsArticles(String query) {
        MutableLiveData<List<Article>> data = new MutableLiveData<>();
        apiService.getLatestHockeyNews("nhl", "publishedAt", "en", "7ddfac08b1254b6693a1c62aa2101b7b")
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            data.setValue(response.body().getArticles());
                        } else {
                            data.setValue(null); // Handle empty or error responses
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        data.setValue(null); // Handle API call failure
                    }
                });
        return data;
    }
}