package com.example.sportstrackerapp.ui.scores;

import java.util.List;

public class GameWeek {
    private final List<Game> games;

    public GameWeek(List<Game> games) {
        this.games = games;
    }

    public List<Game> getGames() {
        return games;
    }
}
