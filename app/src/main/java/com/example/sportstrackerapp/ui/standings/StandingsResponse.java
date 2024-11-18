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
        private int otLosses;
        private TeamCommonName teamCommonName;
        @SerializedName("teamLogo")
        private String teamLogo;

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

        public int getOtLosses() {
            return otLosses;
        }

        public int getPoints() {
            return points;
        }

        public String getTeamLogo() {
            return teamLogo;
        }

        public void setTeamLogo(String teamLogo) {
            this.teamLogo = teamLogo;
        }

        public String getTeamCommonName() {
            if (teamCommonName != null && teamCommonName.getDefaultName() != null) {
                return teamCommonName.getDefaultName();
            } else {
                return "Unknown Team";
            }
        }

        public static class TeamCommonName {
            @SerializedName("default")  // maps default to defaultName
            private String defaultName;

            public String getDefaultName() {
                return defaultName;
            }
        }

    }
}
