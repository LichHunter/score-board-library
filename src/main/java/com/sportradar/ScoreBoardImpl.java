package com.sportradar;

import java.util.HashSet;
import java.util.Set;

public class ScoreBoardImpl implements ScoreBoard {
    private final Set<Game> ongoingGames = new HashSet<>();

    @Override
    public void startGame(String home, String away) {
        // TODO Auto-generated method stub

    }

    @Override
    public void finishGame(String homeTeam, String awayTeam) {
        // TODO Auto-generated method stub

    }

    @Override
    public void getSummary() {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateScore(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        // TODO Auto-generated method stub

    }

    protected Set<Game> getGames() {
        return ongoingGames;
    }
}
