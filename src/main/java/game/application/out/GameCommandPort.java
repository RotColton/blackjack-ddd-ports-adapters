package game.application.out;

import game.application.domain.model.Game;

public interface GameCommandPort {
    Game addGame(Game game);
    Game applyGameChanges(Game game);
}
