package domain;

import game.domain.model.Game;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;


import javax.naming.directory.InvalidAttributeValueException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StartGameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n"})
    void cannotStartGameWithoutValidPlayerName(String playerName) throws InvalidAttributeValueException {

        Exception exception = assertThrows(InvalidAttributeValueException.class,
                () -> Game.startGame(playerName));

        String expectedMessage = "Invalid Player Name";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }




}
