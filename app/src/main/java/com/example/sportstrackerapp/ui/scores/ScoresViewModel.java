package com.example.sportstrackerapp.ui.scores;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ScoresViewModel extends ViewModel {
    private final ScoresRepository scoresRepository = new ScoresRepository();
    private final LiveData<List<GameWeek>> scores = scoresRepository.getScores();

    public LiveData<List<GameWeek>> getScores() {
        return scores;
    }

    public LiveData<List<GameWeek>> getScheduleForDate(String date) {
        return scoresRepository.getScheduleForDate(date);
    }
}
