package game.application.in;

import game.application.domain.model.GameID;

public record PlayerHitCommand(
        GameID id
) {}
