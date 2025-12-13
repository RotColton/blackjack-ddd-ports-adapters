package game.application.domain.model;

import org.junit.jupiter.api.Test;
import java.util.LinkedHashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class PlayerHitTest {

    Game game;

    @Test
    void shouldDealOneCardToPlayer(){

        Hand playerHand = Hand.from(new LinkedHashSet<>(
                List.of(
                        new Card(Suit.CLUBS, Value.QUEEN),
                        new Card(Suit.CLUBS, Value.JACK)
                )));
        Deck deck = Deck.from(new LinkedHashSet<>(
                List.of(
                new Card(Suit.CLUBS, Value.TWO),
                new Card(Suit.HEARTS, Value.NINE)
        )));
        game = Game.from(any(), any(), deck, playerHand, any(), GameStatus.IN_PROGRESS);

        game.playerHit();

        assertEquals(3, game.playerHand().size());

    }

    @Test
    void shouldNotDealCardIfPlayerHas21OrMore(){
        Hand playerHand = Hand.from(new LinkedHashSet<>(
                List.of(
                        new Card(Suit.CLUBS, Value.ACE),
                        new Card(Suit.CLUBS, Value.JACK)
                )));
        game = Game.from(any(), any(), any(), playerHand, any(), GameStatus.IN_PROGRESS);

        game.playerHit();

        assertEquals(2, game.playerHand().size());

    }

    @Test
    void shouldNotDealCardIfGameIsNotInProgress(){
        Hand playerHand = Hand.from(new LinkedHashSet<>(
                List.of(
                        new Card(Suit.CLUBS, Value.TWO),
                        new Card(Suit.CLUBS, Value.FOUR)
                )));

        game = Game.from(any(), any(), any(), playerHand, any(), GameStatus.PLAYER_WON);

        game.playerHit();

        assertEquals(2, game.playerHand().size());

    }
}
