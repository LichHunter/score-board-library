package com.sportradar;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

public class ScoreBoardImpl implements ScoreBoard {
    private final Set<Match> ongoingMatches = new HashSet<>();

    @Override
    public void startMatch(String home, String away) {
        if (home == null || away == null) {
            throw new InvalidParameterException("One of parameters is null");
        }

        Match game = new Match(home, away);

        if (ongoingMatches.contains(game)) {
            throw new MatchAlreadyExistsException(home, away);
        }

        ongoingMatches.add(game);
    }

    @Override
    public void updateScore(String homeTeam, int homeScore, String awayTeam, int awayScore) {
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

    protected Set<Match> getMatches() {
        return ongoingMatches;
    }
}
