package com.sportradar;

import java.util.List;

public interface ScoreBoard {

    void startMatch(String homeTeam, String awayTeam);

    void updateScore(String homeTeam, int homeScore, String awayTeam, int awayScore);

    void finishMatch(String homeTeam, String awayTeam);

    List<Match> getSummary();

}
