package com.sportradar;

public class MatchAlreadyExistsException extends RuntimeException {

    public MatchAlreadyExistsException(String homeTeam, String awayTeam) {
        super(String.format("Match between '%s' and '%s' already exists", homeTeam, awayTeam));
    }

}
