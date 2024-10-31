package com.example.sportstrackerapp.ui.scores;

public class Game {
    private Team homeTeam;
    private Team awayTeam;
    private String startTimeUTC;

    public Team getHomeTeam() {
        return homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public String getGameTime() {
        return startTimeUTC;
    }
}
