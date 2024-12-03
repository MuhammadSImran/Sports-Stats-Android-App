package com.example.sportstrackerapp.ui.scores;

public class Team {
//    private final String placeName;
//    private final String commonName;
    private final String logo;
    private final int score;

    public Team(String logo, int score) {
//        this.placeName = placeName;
//        this.commonName = commonName;
        this.logo = logo;
        this.score = score;
    }

    public String getLogo() {
        return logo;
    }

    public int getScore() {
        return score;
    }

//    public String getName() {
//        return placeName + " " + commonName;
//    }
}
