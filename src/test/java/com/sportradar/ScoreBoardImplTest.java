package com.sportradar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.InvalidParameterException;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

public class ScoreBoardImplTest {
    private ScoreBoardImpl scoreBoard = new ScoreBoardImpl();

    @Test
    void givenHomeAndAwayTeams_whenStartMatch_thenSetCointainsOneMatch() {
        scoreBoard.startMatch("Team1", "Team2");

        assertEquals(1, scoreBoard.getMatches().size());
        assertThat(scoreBoard.getMatches().size()).isEqualTo(1);
    }

    @ParameterizedTest
    @MethodSource("startMatchParameterProvider")
    void givenNullParameter_whenStartMatch_thenThrowException(String team1, String team2) {
        assertThatThrownBy(() -> scoreBoard.startMatch(team1, team2)).isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void givenDuplicateTeamPair_whenStartMatch_thenThrowException() {
        scoreBoard.startMatch("Team1", "Team2");

        assertThatThrownBy(() -> scoreBoard.startMatch("Team1", "Team2")).isInstanceOf(MatchAlreadyExistsException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, -3})
    void givenMatchStartedAndValidParameters_whenUpdateScore_thenScoreShouldBeUpdated(int score) {
        scoreBoard.startMatch("Team1", "Team2");

        scoreBoard.updateScore("Team1", score, "Team2", score);

        Set<Match> matches = scoreBoard.getMatches();
        assertThat(matches.size()).isEqualTo(1);

        for (Match match : matches) {
            assertThat(match.getHomeTeam()).isEqualTo("Team1");
            assertThat(match.getAwayTeam()).isEqualTo("Team2");
            assertThat(match.getHomeScore()).isEqualTo(Math.abs(score));
            assertThat(match.getAwayScore()).isEqualTo(Math.abs(score));
        }
    }

    @Test
    void givenMatchDoesNotExistAndValidParameters_whenUpdateScore_thenThrowException() {
        assertThatThrownBy(() -> scoreBoard.updateScore("Team1", 10, "Team2", 10)).isInstanceOf(MatchDoesNotExistException.class);
    }

    @ParameterizedTest
    @MethodSource("updateScoreParameterProvider")
    void givenNullParameter_whenUpdateScore_thenThrowException(String team1, int team1Score, String team2, int team2Score) {
        scoreBoard.startMatch("Team1", "Team2");

        assertThatThrownBy(() -> scoreBoard.updateScore(team1, team1Score, team2, team2Score)).isInstanceOf(InvalidParameterException.class);
    }

    private static Stream<Arguments> startMatchParameterProvider() {
        return Stream.of(
                         Arguments.of("Team1", null),
                         Arguments.of(null, "Team2"),
                         Arguments.of(null, null)
        );
    }

    private static Stream<Arguments> updateScoreParameterProvider() {
        return Stream.of(
                         Arguments.of("Team1", 0, null, 1),
                         Arguments.of(null, 1, "Team2", 0),
                         Arguments.of(null, 2, null, 3)
        );
    }
}
