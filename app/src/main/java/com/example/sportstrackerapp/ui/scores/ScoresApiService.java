package com.example.sportstrackerapp.ui.scores;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ScoresApiService {
    @GET("schedule/now")
    Call<ScoresResponse> getTodaySchedule();
}