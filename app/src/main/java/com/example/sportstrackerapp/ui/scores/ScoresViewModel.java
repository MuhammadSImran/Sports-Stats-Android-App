package com.example.sportstrackerapp.ui.scores;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ScoresViewModel extends ViewModel {

    private final LiveData<List<GameWeek>> scores;

    public ScoresViewModel() {
        ScoresRepository scoresRepository = new ScoresRepository();
        scores = scoresRepository.getScores();
    }

    public LiveData<List<GameWeek>> getScores() {
        return scores;
    }
}