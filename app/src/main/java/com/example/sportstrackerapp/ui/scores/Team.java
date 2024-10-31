package com.example.sportstrackerapp.ui.scores;

public class Team {
    private final String abbrev;
    private final String logo;

    public Team(String abbrev, String logo) {
        this.abbrev = abbrev;
        this.logo = logo;
    }

    public String getAbbrev() {
        return abbrev;
    }

    public String getLogo() {
        return logo;
    }
}