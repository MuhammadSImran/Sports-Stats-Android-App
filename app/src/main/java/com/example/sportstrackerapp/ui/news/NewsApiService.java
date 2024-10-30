package com.example.sportstrackerapp.ui.news;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {
    @GET("v2/everything")
    Call<NewsResponse> getLatestHockeyNews(
            @Query("q") String query,
            @Query("sortBy") String sortBy,
            @Query("language") String language,
            @Query("apiKey") String apiKey
    );
}
