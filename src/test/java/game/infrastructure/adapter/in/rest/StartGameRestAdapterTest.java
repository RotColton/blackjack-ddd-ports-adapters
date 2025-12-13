package game.infrastructure.adapter.in.rest;

import game.application.domain.model.Game;
import game.application.domain.model.PlayerName;
import game.application.in.StartGameCommand;
import game.application.in.StartGameUseCase;
import game.infrastructure.adapter.in.rest.request.StartGameRequest;
import game.infrastructure.adapter.in.rest.response.StartGameResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


public class StartGameRestAdapterTest {

    RestTestClient restTestClient;
    StartGameUseCase useCase;
    StartGameRequest request;
    Game pepitoGame;

    @BeforeEach
    void setUp(){
        useCase = Mockito.mock(StartGameUseCase.class);
        restTestClient = RestTestClient.bindToController(new StartGameRestAdapter(useCase)).build();
    }

    @Test
    void shouldStartGameAndReturn201() {

        request = new StartGameRequest("Pepito");
        pepitoGame = Game.start(PlayerName.of("Pepito"));
        when(useCase.startGame(
                new StartGameCommand(PlayerName.of("Pepito")))).thenReturn(pepitoGame);

        restTestClient.post().uri("/games/start")
                .body(request)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(StartGameResponse.class)
                .value(game -> {
                    assert game != null;
                    assertEquals(pepitoGame.id(), game.gameID());
                    assertEquals(pepitoGame.playerName(), game.playerName());
                    assertEquals(pepitoGame.playerHand(), game.playerHand());
                    assertEquals(pepitoGame.dealerHand(), game.dealerHand());
                    assertEquals(pepitoGame.deck(), game.deck());
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
