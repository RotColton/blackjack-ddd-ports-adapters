package game.application.domain.event;

import game.application.domain.model.Card;
import game.application.domain.model.GameID;

import java.util.List;

public record PlayerLosesEvent(
        GameID gameID,
        String playerName,
        List<Card> playerHand,
        int score
) {}
