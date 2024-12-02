package com.example.sportstrackerapp.ui.scores;

import java.util.List;

public class ScoresResponse {
    private final List<GameWeek> gameWeek;

    public ScoresResponse(List<GameWeek> gameWeek) {
        this.gameWeek = gameWeek;
    }

    public List<GameWeek> getGameWeek() {
        return gameWeek;
    }
}
