package game.infrastructure.adapter.in.rest;

import game.application.domain.model.*;
import game.application.in.StartGameCommand;
import game.application.in.StartGameUseCase;
import game.infrastructure.adapter.in.rest.request.StartGameRequest;
import game.infrastructure.adapter.in.rest.response.GameResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.client.RestTestClient;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class StartGameRestAdapterTest {

    RestTestClient restTestClient;
    StartGameUseCase useCase;
    StartGameRequest request;
    Game game;

    @BeforeEach
    void setUp(){
        useCase = Mockito.mock(StartGameUseCase.class);
        restTestClient = RestTestClient.bindToController(new StartGameRestAdapter(useCase)).build();
    }

    @Test
    void shouldStartGameAndReturn201() {

        request = new StartGameRequest("Pepito");
        game = Game.start(PlayerName.of("Pepito"));

        when(useCase.startGame(any(StartGameCommand.class))).thenReturn(game);

        restTestClient.post().uri("/games/start")
                .body(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(GameResponse.class)
                .value(gameResponse -> {
                    assert gameResponse != null;
                    assertEquals(game.id(), gameResponse.gameID());
                    assertEquals(game.playerName(), gameResponse.playerName());
                    assertEquals(game.playerHand(), gameResponse.playerHand());
                    assertEquals(game.dealerHand().get(0), gameResponse.upCard());
                    assertEquals(game.status(), gameResponse.status());
                });
    }

    @ParameterizedTest
    @MethodSource("invalidPlayerNames")
    void shouldReturn400ForInvalidNames(String playerName) {

        request = new StartGameRequest(playerName);

        restTestClient.post().uri("/games/start")
                .body(request)
                .exchange()
                .expectStatus()
                .isBadRequest();
    }

   static Stream<String> invalidPlayerNames() {
        return Stream.of(
                null,
                "",
                " ",
                "12",
                "0123456789"
        );
    }
}
