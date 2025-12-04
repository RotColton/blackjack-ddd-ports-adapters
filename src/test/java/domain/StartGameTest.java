package domain;

import game.domain.model.Game;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StartGameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n", "st","0123456789"})
    void shouldNotStartGameWithoutValidPlayerName(String playerName){

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> Game.startGame(playerName));
    }



}
