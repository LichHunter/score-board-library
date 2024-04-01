package com.sportradar;

import static java.util.stream.Collectors.toList;

import java.security.InvalidParameterException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreBoardImpl implements ScoreBoard {
    private final Map<String, Match> ongoingMatches = new HashMap<>();

    @Override
    public void startMatch(String homeTeam, String awayTeam) {
        checkParameters(homeTeam, awayTeam);

        String matchKey = formMatchKey(homeTeam, awayTeam);
        if (isMatchOngoing(matchKey)) {
            throw new MatchAlreadyExistsException(homeTeam, awayTeam);
        }

        ongoingMatches.put(matchKey, new Match(homeTeam, awayTeam));
    }

    @Override
    public void updateScore(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        checkParameters(homeTeam, awayTeam);

        String matchKey = formMatchKey(homeTeam, awayTeam);
        if (!isMatchOngoing(matchKey)) {
            throw new MatchDoesNotExistException(homeTeam, awayTeam);
        }

        Match match = ongoingMatches.get(matchKey);
        match.setHomeScore(Math.abs(homeScore));
        match.setAwayScore(Math.abs(awayScore));
    }

    @Override
    public void finishMatch(String homeTeam, String awayTeam) {
        checkParameters(homeTeam, awayTeam);

        String matchKey = formMatchKey(homeTeam, awayTeam);
        if (!isMatchOngoing(matchKey)) {
            throw new MatchDoesNotExistException(homeTeam, awayTeam);
        }

        ongoingMatches.remove(matchKey);
    }

    @Override
    public List<Match> getSummary() {
        return ongoingMatches.values().stream()
                .sorted(Comparator.comparing((Match match) -> match.getHomeScore() + match.getAwayScore()).thenComparing(Match::getStartTime))
                .collect(toList());
    }

    private boolean isMatchOngoing(String matchKey) {
        return ongoingMatches.containsKey(matchKey);
    }

    private void checkParameters(String homeTeam, String awayTeam) {
        if (homeTeam == null || awayTeam == null) {
            throw new InvalidParameterException("One of parameters is null");
        }
    }

    private String formMatchKey(String homeTeam, String awayTeam) {
        return homeTeam + "," + awayTeam;
    }

    Map<String, Match> getMatches() {
        return ongoingMatches;
    }
}
