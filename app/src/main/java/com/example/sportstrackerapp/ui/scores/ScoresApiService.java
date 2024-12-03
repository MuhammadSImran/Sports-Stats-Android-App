package com.example.sportstrackerapp.ui.scores;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ScoresApiService {
    @GET("schedule/now")
    Call<ScoresResponse> getTodaySchedule();

    @GET("schedule/{date}")
    Call<ScoresResponse> getScheduleByDate(@Path("date") String date);
}
