package com.example.sportstrackerapp.ui.favouriteTeams;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sportstrackerapp.ui.favouriteTeams.models.Team;

import java.util.ArrayList;
import java.util.List;

public class FavouriteTeamsViewModel extends ViewModel {

    private final MutableLiveData<List<Team>> favouriteTeams;

    public FavouriteTeamsViewModel() {
        favouriteTeams = new MutableLiveData<>(new ArrayList<>());
    }

    public LiveData<List<Team>> getFavouriteTeams() {
        return favouriteTeams;
    }

    public void addTeam(Team team) {
        List<Team> currentTeams = favouriteTeams.getValue();
        if (currentTeams != null) {
            currentTeams.add(team);
            favouriteTeams.setValue(currentTeams);
        }
    }

    public void removeTeam(Team team) {
        List<Team> currentTeams = favouriteTeams.getValue();
        if (currentTeams != null) {
            currentTeams.remove(team);
            favouriteTeams.setValue(currentTeams);
        }
    }

    public void clearAllTeams() {
        List<Team> currentTeams = favouriteTeams.getValue();
        if (currentTeams != null) {
            currentTeams.clear();
            favouriteTeams.setValue(currentTeams);
        }
    }
}
