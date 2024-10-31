package com.example.sportstrackerapp.ui.standings;

import com.example.sportstrackerapp.ui.standings.StandingsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface StandingsApiService {
    @GET("v1/standings/now")  // NHL standings endpoint
    Call<StandingsResponse> getNHLStandings();
}
