package game.application.domain.event;

import game.application.domain.model.Card;

import java.util.List;
import java.util.UUID;

public record PlayerWinsEvent(
        UUID gameID,
        String playerName,
        List<Card> playerHand,
        int score
) {}
