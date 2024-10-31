package com.example.sportstrackerapp.ui.scores;

import java.util.List;

public class GameWeek {
    private String date;
    private String dayAbbrev;
    private int numberOfGames;
    private List<Game> games;

    public String getDate() {
        return date;
    }

    public String getDayAbbrev() {
        return dayAbbrev;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public List<Game> getGames() {
        return games;
    }
}

