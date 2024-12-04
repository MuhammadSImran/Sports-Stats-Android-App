package com.example.sportstrackerapp.ui.scores;

import com.google.gson.annotations.SerializedName;

public class Team {
    private final Name commonName;
    private final String logo;
    private final int score;

    public Team(Name commonName, String logo, int score) {
        this.commonName = commonName;
        this.logo = logo;
        this.score = score;
    }

    public String getName() {
        return commonName.getDefaultName();
    }

    public String getLogo() {
        return logo;
    }

    public int getScore() {
        return score;
    }

    public static class Name {
        @SerializedName("default")
        private final String defaultName;

        public Name(String defaultName) {
            this.defaultName = defaultName;
        }

        public String getDefaultName() {
            return defaultName;
        }
    }
}