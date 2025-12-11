package game.application.in;

import game.application.domain.model.PlayerName;

public record StartGameCommand(
        PlayerName playerName) {
}
