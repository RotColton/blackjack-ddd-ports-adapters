package game.infrastructure.adapter.in.rest;

import game.application.domain.model.*;
import game.application.in.PlayerStandCommand;
import game.application.in.PlayerStandUseCase;
import game.infrastructure.adapter.in.rest.response.GameResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.client.RestTestClient;
import java.util.LinkedHashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PlayerStandRestAdapterTest {

    PlayerStandUseCase useCase;
    RestTestClient restTestClient;
    Game game;
    Deck emptyDeck;
    GameID gameID;
    PlayerName name;
    Hand emptyHand;
    GameStatus inProgressStatus;

    @BeforeEach
    void setUp(){
        useCase = Mockito.mock(PlayerStandUseCase.class);
        restTestClient = RestTestClient.bindToController(new PlayerStandRestAdapter(useCase)).build();
        emptyDeck = Deck.from(new LinkedHashSet<>());
        gameID = new GameID(UUID.randomUUID());
        name = PlayerName.of("Pepito");
        emptyHand = Hand.from(new LinkedHashSet<Card>());
        inProgressStatus = GameStatus.IN_PROGRESS;
    }


    @Test
    void shouldReturn200WhenPlayerStands() {

        game = Game.from(
                gameID,
                name,
                emptyDeck,
                emptyHand,
                emptyHand,
                inProgressStatus
        );

        when(useCase.stand(any(PlayerStandCommand.class))).thenReturn(game);

        restTestClient.put()
                .uri(uriBuilder ->
                        uriBuilder.path("/games/stand")
                                .queryParam("gameId", game.id().id())
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(GameResponse.class)
                .value(standResponse -> {
                    assert standResponse != null;
                    assertEquals(game.id(), standResponse.gameID());
                    assertEquals(game.playerName(), standResponse.playerName());
                    assertEquals(game.playerHand(), standResponse.playerHand());
                    assertEquals(game.dealerHand(), standResponse.dealerHand());
                    assertEquals(game.status(), standResponse.status());
                });
    }


}
