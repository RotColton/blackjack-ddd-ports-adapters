package game.infrastructure.adapter.in.rest;

import game.application.domain.model.*;
import game.application.in.PlayerHitCommand;
import game.application.in.PlayerHitUseCase;
import game.infrastructure.adapter.in.rest.response.GameResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.client.RestTestClient;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PlayerHitRestAdapterTest {

    RestTestClient restTestClient;
    PlayerHitUseCase useCase;
    Game game;
    Deck emptyDeck;
    GameID gameID;
    PlayerName name;
    Hand emptyHand;
    GameStatus inProgressStatus;


    @BeforeEach
    void setUp(){
        //Todo: Mocking methods declared on non-public parent classes is not supported.
        useCase = Mockito.mock(PlayerHitUseCase.class);
        restTestClient = RestTestClient.bindToController(new PlayerHitRestAdapter(useCase)).build();
        emptyDeck = Deck.from(new LinkedHashSet<>());
        gameID = new GameID(UUID.randomUUID());
        name = PlayerName.of("Pepito");
        emptyHand = Hand.from(new LinkedHashSet<Card>());
        inProgressStatus = GameStatus.IN_PROGRESS;
    }

    @Test
    void shouldPlayerHitAndReturn200() {
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
                emptyHand,
                dealerHand,
                inProgressStatus
        );

        when(useCase.hit(any(PlayerHitCommand.class))).thenReturn(game);

        restTestClient.put()
                .uri(uriBuilder ->
                        uriBuilder.path("/games/hit")
                                .queryParam("gameId", game.id().id())
                                .build())
                .exchange()
                    .expectStatus().isOk()
                .expectBody(GameResponse.class)
                .value(hitResponse -> {
                    assert hitResponse != null;
                    assertEquals(game.id(), hitResponse.gameID());
                    assertEquals(game.playerName(), hitResponse.playerName());
                    assertEquals(game.playerHand(), hitResponse.playerHand());
                    assertEquals(game.status(), hitResponse.status());
                });
    }

}
