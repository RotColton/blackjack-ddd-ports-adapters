package game.application.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class PlayerHitTest {

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
    void shouldDealOneCardToPlayer(){

        Hand playerHand = Hand.from(new LinkedHashSet<>(
                List.of(
                        new Card(Suit.CLUBS, Value.SIX),
                        new Card(Suit.CLUBS, Value.JACK)
                )));

        Deck deck = Deck.from(new LinkedHashSet<>(
                List.of(
                        new Card(Suit.SPADES, Value.ACE),
                        new Card(Suit.CLUBS, Value.ACE)
                )
        ));

        game = Game.from(
                gameID,
                name,
                deck,
                playerHand,
                emptyHand,
                inProgressStatus);

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

        game = Game.from(
                gameID,
                name,
                emptyDeck,
                playerHand,
                emptyHand,
                inProgressStatus
        );

        assertEquals(2, game.playerHand().size());

    }

    @Test
    void shouldNotDealCardIfGameIsNotInProgress(){

        game = Game.from(
                gameID,
                name,
                emptyDeck,
                emptyHand,
                emptyHand,
                GameStatus.PLAYER_WON
        );

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

        game = Game.from(
                gameID,
                name,
                deck,
                playerHand,
                dealerHand,
                inProgressStatus
        );

        game.playerHit();

        assertEquals(GameStatus.PUSH, game.status());

    }

}
