package game.infrastructure.adapter.in.rest.response;

import game.application.domain.model.Card;
import game.application.domain.model.PlayerName;

import java.util.List;
import java.util.UUID;

public record StartGameResponse(
        UUID gameID,
        List<Card> deck,
        PlayerName playerName,
        List<Card> playerHand,
        Card upCard) {

}
