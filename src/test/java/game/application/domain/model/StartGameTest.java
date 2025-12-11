package game.application.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class StartGameTest {

    private Game game;

    @BeforeEach
    void setUp(){
        game = Game.start(PlayerName.of("Pepito"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n", "st","0123456789"})
    void shouldNotStartGameWithoutValidPlayerName(String playerName){

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> Game.start(PlayerName.of(playerName)));
    }

    @Test
    void shouldDealtTwoCardsToThePlayerAndTwoToTheDealer(){

        assertEquals(2, game.playerHand().size());
        assertEquals(2, game.dealerHand().size());
        assertEquals(48, game.deck().size());

    }

}
