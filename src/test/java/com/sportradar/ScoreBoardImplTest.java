package com.sportradar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.InvalidParameterException;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ScoreBoardImplTest {
    private ScoreBoardImpl scoreBoard = new ScoreBoardImpl();

    void givenHomeAndAwayTeams_whenStartGame_thenSetCointainsOneGame() {
        scoreBoard.startGame("Team1", "Team2");

        assertEquals(1, scoreBoard.getGames().size());
        assertThat(scoreBoard.getGames().size()).isEqualTo(1);
    }

    @ParameterizedTest
    @MethodSource("startGameParameterProvider")
    void givenNullParameter_whenStartGame_thenThrowException(String team1, String team2) {
        assertThatThrownBy(() -> scoreBoard.startGame(team1, team2)).isInstanceOf(InvalidParameterException.class);
    }

    void givenDuplicateTeamPair_whenStartGame_thenThrowException() {

        scoreBoard.startGame("Team1", "Team2");

        assertThatThrownBy(() -> scoreBoard.startGame("Team1", "Team2")).isInstanceOf(MatchAlreadyExistsException.class);
    }

    private static Stream<Arguments> startGameParameterProvider() {
        return Stream.of(
                         Arguments.of("Team1", null),
                         Arguments.of(null, "Team2"),
                         Arguments.of(null, null)
        );
    }
}
