package com.example.sportstrackerapp.ui.predictions;

public class GameData {
    private final String homeTeam;
    private final String awayTeam;
    private final String homeOdds;
    private final String awayOdds;
    private final String homeLogoUrl;
    private final String awayLogoUrl;

    public GameData(String homeTeam, String awayTeam, String homeOdds, String awayOdds, String homeLogoUrl, String awayLogoUrl) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeOdds = homeOdds;
        this.awayOdds = awayOdds;
        this.homeLogoUrl = homeLogoUrl;
        this.awayLogoUrl = awayLogoUrl;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public String getHomeOdds() {
        return homeOdds;
    }

    public String getAwayOdds() {
        return awayOdds;
    }

    public String getHomeLogoUrl() {
        return homeLogoUrl;
    }

    public String getAwayLogoUrl() {
        return awayLogoUrl;
    }
}
