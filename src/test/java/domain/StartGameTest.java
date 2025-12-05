package domain;

import game.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class StartGameTest {

    private Game game;

    @BeforeEach
    void setUp(){
        game = Game.start("Pepito");
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"  ", "\t", "\n", "st","0123456789"})
    void shouldNotStartGameWithoutValidPlayerName(String playerName){

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> Game.start(playerName));
    }

    @Test
    void shouldDealtTwoCardsToThePlayerAndTwoToTheDealer(){

        assertEquals(2, game.playerHand().size());
        assertEquals(2, game.dealerHand().size());
        assertEquals(48, game.deck().size());

    }

}
