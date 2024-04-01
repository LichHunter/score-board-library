package com.sportradar;

import static java.util.stream.Collectors.toList;

import java.security.InvalidParameterException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScoreBoardImpl implements ScoreBoard {
    private final Set<Match> ongoingMatches = new HashSet<>();

    @Override
    public void startMatch(String homeTeam, String awayTeam) {
        checkParameters(homeTeam, awayTeam);

        if (containsGame(homeTeam, awayTeam)) {
            throw new MatchAlreadyExistsException(homeTeam, awayTeam);
        }

        ongoingMatches.add(new Match(homeTeam, awayTeam));
    }

    @Override
    public void updateScore(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        checkParameters(homeTeam, awayTeam);

        if (!containsGame(homeTeam, awayTeam)) {
            throw new MatchDoesNotExistException(homeTeam, awayTeam);
        }

        for (Match match : ongoingMatches) {
            if (match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam)) {
                match.setHomeScore(Math.abs(homeScore));
                match.setAwayScore(Math.abs(awayScore));
            }
        }
    }

    @Override
    public void finishMatch(String homeTeam, String awayTeam) {
        checkParameters(homeTeam, awayTeam);

        if (!containsGame(homeTeam, awayTeam)) {
            throw new MatchDoesNotExistException(homeTeam, awayTeam);
        }

        ongoingMatches.remove(new Match(homeTeam, awayTeam));
    }

    @Override
    public List<Match> getSummary() {
        return ongoingMatches.stream()
            .sorted(Comparator.comparing((Match match) -> match.getHomeScore() + match.getAwayScore()).thenComparing(Match::getStartTime))
            .collect(toList());
    }

    Set<Match> getMatches() {
        return ongoingMatches;
    }

    private boolean containsGame(String homeTeam, String awayTeam) {
        return ongoingMatches.contains(new Match(homeTeam, awayTeam));
    }

    private void checkParameters(String homeTeam, String awayTeam) {
        if (homeTeam == null || awayTeam == null) {
            throw new InvalidParameterException("One of parameters is null");
        }
    }
}
