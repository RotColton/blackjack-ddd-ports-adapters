package game.infrastructure.adapter.in.rest;

import game.application.domain.model.*;
import game.application.in.PlayerStandCommand;
import game.application.in.PlayerStandUseCase;
import game.application.out.GameOutputPort;
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

public class PlayerStandRestAdapterTest {

    PlayerStandUseCase useCase;
    RestTestClient restTestClient;
    GameOutputPort outputPort;

    @BeforeEach
    void setUp(){
        useCase = Mockito.mock(PlayerStandUseCase.class);
        restTestClient = RestTestClient.bindToController(new PlayerStandRestAdapter(useCase)).build();
    }


    @Test
    void shouldReturn200WhenPlayerStands() {

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

        Hand dealerHand = Hand.from(new LinkedHashSet<>(
                List.of(
                        new Card(Suit.HEARTS, Value.SIX),
                        new Card(Suit.CLUBS, Value.QUEEN)
                )
        ));

        Game game = Game.from(
                UUID.randomUUID(),
                PlayerName.of("Pepito"),
                deck,
                playerHand,
                dealerHand,
                GameStatus.IN_PROGRESS);

        when(useCase.stand(any(PlayerStandCommand.class))).thenReturn(game);

        restTestClient.put()
                .uri(uriBuilder ->
                        uriBuilder.path("/games/stand")
                                .queryParam("gameId", game.id())
                                .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(GameResponse.class)
                .value(standResponse -> {
                    assert standResponse != null;
                    assertEquals(game.id(), standResponse.gameID());
                    assertEquals(game.playerName(), standResponse.playerName());
                    assertEquals(game.playerHand(), standResponse.playerHand());
                    assertEquals(game.dealerHand().get(0), standResponse.upCard());
                    assertEquals(game.status(), standResponse.status());
                });
    }


}
