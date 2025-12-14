package game.infrastructure.adapter.in.rest.response;

import game.application.domain.model.Card;
import game.application.domain.model.GameID;
import game.application.domain.model.GameStatus;
import game.application.domain.model.PlayerName;
import java.util.List;

//Todo: change to UL
public record GameResponse(
        GameID gameID,
        PlayerName playerName,
        List<Card> playerHand,
        List<Card> dealerHand,
        GameStatus status
) {}
