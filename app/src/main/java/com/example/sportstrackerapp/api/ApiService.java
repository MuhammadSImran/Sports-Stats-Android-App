package com.example.sportstrackerapp.api;

import com.example.sportstrackerapp.ui.news.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("v2/sports/hockey/nhl/news")  // ESPN NHL news endpoint
    Call<NewsResponse> getNHLNews();
}