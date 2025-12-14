package game.application.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


public class PlayerStandTest {

    Game game;
    Deck emptyDeck;
    GameID gameID;
    PlayerName name;
    Hand emptyHand;
    GameStatus inProgressStatus;

    @BeforeEach
    void setUp(){
        emptyDeck = Deck.from(new LinkedHashSet<>());
        gameID = new GameID(UUID.randomUUID());
        name = PlayerName.of("Pepito");
        emptyHand = Hand.from(new LinkedHashSet<Card>());
        inProgressStatus = GameStatus.IN_PROGRESS;
    }

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

        game = Game.from(
                gameID,
                name,
                deck,
                playerHand,
                dealerHand,
                inProgressStatus
        );

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

        game = Game.from(
                gameID,
                name,
                emptyDeck,
                playerHand,
                dealerHand,
                inProgressStatus
        );

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

        game = Game.from(
                gameID,
                name,
                emptyDeck,
                playerHand,
                dealerHand,
                inProgressStatus
        );

        game.playerStand();

        assertEquals(GameStatus.PLAYER_WON, game.status());
    }



}
