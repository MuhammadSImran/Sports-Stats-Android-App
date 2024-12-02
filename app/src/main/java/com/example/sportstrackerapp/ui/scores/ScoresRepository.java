package com.example.sportstrackerapp.ui.scores;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScoresRepository {
    private final ScoresApiService apiService;

    public ScoresRepository() {
        apiService = ScoresApiClient.getRetrofit().create(ScoresApiService.class);
    }

    public LiveData<List<GameWeek>> getScores() {
        MutableLiveData<List<GameWeek>> data = new MutableLiveData<>();
        apiService.getTodaySchedule().enqueue(new Callback<ScoresResponse>() {
            @Override
            public void onResponse(@NonNull Call<ScoresResponse> call, @NonNull Response<ScoresResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getGameWeek());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ScoresResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<List<GameWeek>> getScheduleForDate(String date) {
        MutableLiveData<List<GameWeek>> data = new MutableLiveData<>();
        apiService.getScheduleByDate(date).enqueue(new Callback<ScoresResponse>() {
            @Override
            public void onResponse(@NonNull Call<ScoresResponse> call, @NonNull Response<ScoresResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    data.setValue(response.body().getGameWeek());
                } else {
                    data.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ScoresResponse> call, @NonNull Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
