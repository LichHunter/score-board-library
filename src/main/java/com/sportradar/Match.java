package com.sportradar;

import java.time.Instant;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = {"startTime", "homeScore", "awayScore"})
public class Match {
    private final String homeTeam;
    private final String awayTeam;

    // Variables excluded from equals and hashcode
    private final Instant startTime;
    private int homeScore;
    private int awayScore;

    public Match(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.startTime = Instant.now();
    }
}
