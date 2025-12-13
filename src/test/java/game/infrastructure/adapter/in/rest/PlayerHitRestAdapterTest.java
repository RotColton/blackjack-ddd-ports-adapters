package game.infrastructure.adapter.in.rest;

import game.application.domain.model.Game;
import game.application.domain.model.PlayerName;
import game.application.in.PlayerHitCommand;
import game.application.in.PlayerHitUseCase;
import game.infrastructure.adapter.in.rest.response.GameResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PlayerHitRestAdapterTest {

    RestTestClient restTestClient;
    PlayerHitUseCase useCase;
    Game game;


    @BeforeEach
    void setUp(){
        useCase = Mockito.mock(PlayerHitUseCase.class);
        restTestClient = RestTestClient.bindToController(new PlayerHitRestAdapter(useCase)).build();
    }

    @Test
    void shouldPlayerHitAndReturn200() {

        game = Game.start(PlayerName.of("Pepito"));

        when(useCase.hit(any(PlayerHitCommand.class))).thenReturn(game);

        restTestClient.put()
                .uri(uriBuilder ->
                        uriBuilder.path("/games/hit")
                                .queryParam("gameId", game.id())
                                .build())
                .exchange()
                    .expectStatus().isOk()
                .expectBody(GameResponse.class)
                .value(hitResponse -> {
                    assert hitResponse != null;
                    assertEquals(game.id(), hitResponse.gameID());
                    assertEquals(game.playerName(), hitResponse.playerName());
                    assertEquals(game.playerHand(), hitResponse.playerHand());
                    assertEquals(game.dealerHand().get(0), hitResponse.upCard());
                    assertEquals(game.status(), hitResponse.status());
                });
    }




}
