package game.application.domain.event;

import java.util.UUID;

public record GameFinishedEvent(
        UUID gameID,
        String playerName,
        String result,
        int playerScore,
        int delerScore
) {}
