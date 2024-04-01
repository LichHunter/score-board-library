package com.sportradar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.InvalidParameterException;
import java.util.List;
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

        assertThatThrownBy(() -> scoreBoard.startMatch("Team1", "Team2"))
                .isInstanceOf(MatchAlreadyExistsException.class);
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, -3 })
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
        assertThatThrownBy(() -> scoreBoard.updateScore("Team1", 10, "Team2", 10))
                .isInstanceOf(MatchDoesNotExistException.class);
    }

    @ParameterizedTest
    @MethodSource("updateScoreParameterProvider")
    void givenNullParameter_whenUpdateScore_thenThrowException(String team1, int team1Score, String team2,
            int team2Score) {
        scoreBoard.startMatch("Team1", "Team2");

        assertThatThrownBy(() -> scoreBoard.updateScore(team1, team1Score, team2, team2Score))
                .isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void givenValidParameters_whenFinishMatch_thenThereIsNoOngoingMatches() {
        scoreBoard.startMatch("Team1", "Team2");

        scoreBoard.finishMatch("Team1", "Team2");

        assertThat(scoreBoard.getMatches().size()).isEqualTo(0);
    }

    @Test
    void givenMatchDoesNotExist_whenFinishMatch_thenThrowException() {
        assertThatThrownBy(() -> scoreBoard.finishMatch("Team1", "Team2"))
                .isInstanceOf(MatchDoesNotExistException.class);
    }

    @ParameterizedTest
    @MethodSource("finishMatchParameterProvider")
    void givenNullParameter_whenFinishMatch_thenThrowException(String team1, String team2) {
        scoreBoard.startMatch("Team1", "Team2");

        assertThatThrownBy(() -> scoreBoard.finishMatch(team1, team2)).isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void givenNoGamesExist_whenGetSummary_thenReturnEmptyList() {
        assertThat(scoreBoard.getSummary()).isEmpty();;
    }

    @Test
    void givenPreExisingGames_whenGetSummary_thenReturnListInProperOrder() {
        scoreBoard.startMatch("Mixico", "Canada");
        scoreBoard.startMatch("Spain", "Brazil");
        scoreBoard.startMatch("Germany", "France");
        scoreBoard.startMatch("Uruguay", "Italy");
        scoreBoard.startMatch("Argentina", "Australia");
        scoreBoard.updateScore("Mixico", 0, "Canada", 5);
        scoreBoard.updateScore("Spain", 10, "Brazil", 2);
        scoreBoard.updateScore("Germany", 2, "France", 2);
        scoreBoard.updateScore("Uruguay", 6, "Italy", 6);
        scoreBoard.updateScore("Argentina", 3, "Australia", 1);

        List<Match> actual = scoreBoard.getSummary();

        assertThat(actual).containsExactlyInAnyOrder(new Match("Uruguay", "Italy"), new Match("Spain", "Brazil"), new Match("Mexico", "Canada"), new Match("Argentina", "Australia"), new Match("Germany", "France"));
    }

    private static Stream<Arguments> startMatchParameterProvider() {
        return Stream.of(
                Arguments.of("Team1", null),
                Arguments.of(null, "Team2"),
                Arguments.of(null, null));
    }

    private static Stream<Arguments> updateScoreParameterProvider() {
        return Stream.of(
                Arguments.of("Team1", 0, null, 1),
                Arguments.of(null, 1, "Team2", 0),
                Arguments.of(null, 2, null, 3));
    }

    private static Stream<Arguments> finishMatchParameterProvider() {
        return Stream.of(
                Arguments.of("Team1", null),
                Arguments.of(null, "Team2"),
                Arguments.of(null, null));
    }

}
