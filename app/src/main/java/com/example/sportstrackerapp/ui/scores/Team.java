package com.example.sportstrackerapp.ui.scores;

public class Team {
    private final String logo;
    private final int score;

    public Team(String logo, int score) {
        this.logo = logo;
        this.score = score;
    }

    public String getLogo() {
        return logo;
    }

    public int getScore() {
        return score;
    }
}
