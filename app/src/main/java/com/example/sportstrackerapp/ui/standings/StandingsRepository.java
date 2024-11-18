package com.example.sportstrackerapp.ui.standings;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

//import com.example.sportstrackerapp.ApiClient;
//import com.example.sportstrackerapp.StandingsApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StandingsRepository {  // manages the API call to standings & handles data for StandingsFragment
    private final StandingsApiService apiService;

    public StandingsRepository() {
        apiService = ApiClient.getRetrofit().create(StandingsApiService.class);
    }

    public LiveData<StandingsResponse> getStandings() {
        MutableLiveData<StandingsResponse> data = new MutableLiveData<>();
        apiService.getNHLStandings().enqueue(new Callback<StandingsResponse>() {
            @Override
            public void onResponse(Call<StandingsResponse> call, Response<StandingsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("StandingsRepository", "Data received: " + response.body().toString());
                    data.setValue(response.body());
                } else {
                    Log.e("StandingsRepository", "API call successful but response is empty");
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<StandingsResponse> call, Throwable t) {
                Log.e("StandingsRepository", "API call failed: " + t.getMessage());
                data.setValue(null);
            }
        });
        return data;
    }
}
