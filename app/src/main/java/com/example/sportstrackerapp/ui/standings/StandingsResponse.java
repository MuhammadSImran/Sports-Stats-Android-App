package com.example.sportstrackerapp.ui.standings;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StandingsResponse {
    private List<Standing> standings;

    public List<Standing> getStandings() {
        return standings;
    }

    public static class Standing {  // represents each team’s standing data
        private String conferenceName;
        private String divisionName;
        private int gamesPlayed;
        private int wins;
        private int losses;
        private int points;
        private TeamName teamName;

        public String getConferenceName() {
            return conferenceName;
        }

        public String getDivisionName() {
            return divisionName;
        }

        public int getGamesPlayed() {
            return gamesPlayed;
        }

        public int getWins() {
            return wins;
        }

        public int getLosses() {
            return losses;
        }

        public int getPoints() {
            return points;
        }

        public String getTeamDefaultName() {
            if (teamName != null && teamName.getDefaultName() != null) {
                return teamName.getDefaultName();
            } else {
                return "Unknown Team";
            }
        }

        public static class TeamName {
            @SerializedName("default")  // maps default to defaultName
            private String defaultName;

            public String getDefaultName() {
                return defaultName;
            }
        }
    }
}
