package game.application.domain.model;

import org.junit.jupiter.api.Test;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class PlayerStandTest {

    @Test
    void shouldPlayerWinsWhenDealerBust(){
        Hand playerHand = Hand.from(new LinkedHashSet<>(
                List.of(
                        new Card(Suit.SPADES, Value.FOUR),
                        new Card(Suit.SPADES, Value.TWO),
                        new Card(Suit.SPADES, Value.QUEEN)
                )
        ));

        Hand dealerHand = Hand.from(new LinkedHashSet<>(
                List.of(
                        new Card(Suit.HEARTS, Value.SIX),
                        new Card(Suit.CLUBS, Value.QUEEN)
                )
        ));

        Deck deck = Deck.from(new LinkedHashSet<>(
                List.of(
                        new Card(Suit.HEARTS, Value.KING),
                        new Card(Suit.HEARTS, Value.NINE),
                        new Card(Suit.CLUBS, Value.JACK)
                )
        ));

        Game game = Game.from(
                UUID.randomUUID(),
                PlayerName.of("Pepito"),
                deck,
                playerHand,
                dealerHand,
                GameStatus.IN_PROGRESS);

        game.playerStand();
        assertEquals(GameStatus.PLAYER_WON, game.status());

    }

    @Test
    void shouldPlayerLosesWhenDealerHasBlackJack(){
        Hand playerHand = Hand.from(new LinkedHashSet<>(
                List.of(
                        new Card(Suit.SPADES, Value.FOUR),
                        new Card(Suit.SPADES, Value.TWO),
                        new Card(Suit.SPADES, Value.QUEEN)
                )
        ));

        Hand dealerHand = Hand.from(new LinkedHashSet<>(
                List.of(
                        new Card(Suit.CLUBS, Value.QUEEN),
                        new Card(Suit.CLUBS, Value.ACE)
                )
        ));

        Game game = Game.from(
                UUID.randomUUID(),
                PlayerName.of("Pepito"),
                any(),
                playerHand,
                dealerHand,
                GameStatus.IN_PROGRESS);

        game.playerStand();

        assertEquals(GameStatus.DEALER_WON, game.status());

    }

    @Test
    void shouldPlayerWinsWhenDealerHasLowerScore() {
        Hand playerHand = Hand.from(new LinkedHashSet<>(
                List.of(
                        new Card(Suit.SPADES, Value.FOUR),
                        new Card(Suit.SPADES, Value.FIVE),
                        new Card(Suit.SPADES, Value.QUEEN)
                )
        ));

        Hand dealerHand = Hand.from(new LinkedHashSet<>(
                List.of(
                        new Card(Suit.CLUBS, Value.QUEEN),
                        new Card(Suit.CLUBS, Value.SEVEN)
                )
        ));

        Game game = Game.from(
                UUID.randomUUID(),
                PlayerName.of("Pepito"),
                any(),
                playerHand,
                dealerHand,
                GameStatus.IN_PROGRESS);

        game.playerStand();

        assertEquals(GameStatus.PLAYER_WON, game.status());
    }



}
