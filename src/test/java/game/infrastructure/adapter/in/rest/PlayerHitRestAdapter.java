package game.infrastructure.adapter.in.rest;

import game.application.domain.model.Game;
import game.application.domain.model.PlayerName;
import game.application.in.PlayerHitCommand;
import game.application.in.PlayerHitUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PlayerHitRestAdapter {
/*
    RestTestClient restTestClient;
    PlayerHitUseCase useCase;
   // PlayerHitRequest request;
    Game game;


    @BeforeEach
    void setUp(){
        useCase = Mockito.mock(PlayerHitUseCase.class);
        restTestClient = RestTestClient.bindToController(new PlayerHitRestAdapter(useCase)).build();
    }

    @Test
    void shouldPlayerHitAndReturn200() {

        request = new PlayerHitRequest(any());
        game = Game.start(PlayerName.of("Pepito"));

        when(useCase.hit(
                new PlayerHitCommand(any()))).thenReturn(game);

        restTestClient.post().uri("/games/hit)
                .body(request)
                .exchange()
                .expectStatus().isOk()
                .expectBody(HitResponse.class)
                .value(hitResponse -> {
                    assert hitResponse != null;
                    assertEquals(game.id(), hitResponse.gameID());
                    assertEquals(game.playerName(), hitResponse.playerName());
                    assertEquals(game.playerHand(), hitResponse.playerHand());
                    assertEquals(game.dealerHand().get(0), hitResponse.upCard());
                    assertEquals(game.status(), hitResponse.status());
                });
    }


*/

}
