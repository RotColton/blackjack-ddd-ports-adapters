package domain;

import game.domain.model.Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StartGameTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n", "st","0123456789"})
    void shouldNotStartGameWithoutValidPlayerName(String playerName){

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> Game.start(playerName));
    }

    @Test
    void shouldDealtTwoCardsToThePlayerAndTwoToTheDealer(){
        Game game = Game.start("Pepito");

        assertEquals(2, game.playerHand().size());
        assertEquals(2, game.dealerHand().size());
        assertEquals(48, game.deck().size());

    }




}
