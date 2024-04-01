package com.sportradar;

public interface ScoreBoard {

    void startMatch(String home, String away);

    void updateScore(String homeTeam, int homeScore, String awayTeam, int awayScore);

    void finishGame(String homeTeam, String awayTeam);

    void getSummary();

}
