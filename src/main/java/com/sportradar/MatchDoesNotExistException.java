package com.sportradar;

public class MatchDoesNotExistException extends RuntimeException {

    public MatchDoesNotExistException(String homeTeam, String awayTeam) {
        super(String.format("Match between '%s' and '%s' does not exist", homeTeam, awayTeam));
    }

}
