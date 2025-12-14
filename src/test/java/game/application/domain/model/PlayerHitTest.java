package game.application.domain.model;

import org.junit.jupiter.api.Test;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

public class PlayerHitTest {

    Game game;

    @Test
    void shouldDealOneCardToPlayer(){

        Hand playerHand = Hand.from(new LinkedHashSet<>(
                List.of(
                        new Card(Suit.CLUBS, Value.SIX),
                        new Card(Suit.CLUBS, Value.JACK)
                )));
        Deck deck = Deck.from(new LinkedHashSet<>(
                List.of(
                new Card(Suit.CLUBS, Value.TWO),
                new Card(Suit.HEARTS, Value.NINE)
        )));
        //todo change any()s
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
        Deck deck = Deck.from(new LinkedHashSet<>(
                List.of(
                        new Card(Suit.CLUBS, Value.TWO),
                        new Card(Suit.HEARTS, Value.NINE)
                )));

        //Todo: change any()
        game = Game.from(UUID.randomUUID(), PlayerName.of("Pepito"), deck, playerHand, any(), GameStatus.IN_PROGRESS);

        assertEquals(2, game.playerHand().size());

    }

    @Test
    void shouldNotDealCardIfGameIsNotInProgress(){
        Hand playerHand = Hand.from(new LinkedHashSet<>(
                List.of(
                        new Card(Suit.CLUBS, Value.TWO),
                        new Card(Suit.CLUBS, Value.FOUR)
                )));
        //todo change any()s
        game = Game.from(any(), any(), any(), playerHand, any(), GameStatus.PLAYER_WON);

        assertThrows(IllegalStateException.class,
                () -> game.playerHit());
    }


    @Test
    void shouldEndGameInBlackJackPush(){
        Hand playerHand = Hand.from(new LinkedHashSet<>(
                List.of(
                        new Card(Suit.HEARTS, Value.KING),
                        new Card(Suit.CLUBS, Value.QUEEN)
                )
        ));

        Hand dealerHand = Hand.from(new LinkedHashSet<>(
                List.of(
                        new Card(Suit.HEARTS, Value.JACK),
                        new Card(Suit.DIAMONDS, Value.ACE)
                )
        ));

        Deck deck = Deck.from(new LinkedHashSet<>(
                List.of(
                        new Card(Suit.SPADES, Value.ACE),
                        new Card(Suit.CLUBS, Value.ACE)
                )
        ));
        //todo change any()s
        Game game = Game.from(any(), any(), deck, playerHand, dealerHand, GameStatus.IN_PROGRESS);
        game.playerHit();

        assertEquals(GameStatus.PUSH, game.status());

    }

}
