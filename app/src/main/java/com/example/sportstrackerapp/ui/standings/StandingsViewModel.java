package com.example.sportstrackerapp.ui.standings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class StandingsViewModel extends ViewModel { // Use the repository & provide standings data to StandingsFragment

    private LiveData<StandingsResponse> standings;
    private final StandingsRepository standingsRepository;

    public StandingsViewModel() {
        standingsRepository = new StandingsRepository();
        standings = standingsRepository.getStandings();
    }

    public LiveData<StandingsResponse> getStandings() {
        return standings;
    }
}