package game.application.out;

import game.application.domain.model.Game;
import game.application.domain.model.GameID;
import game.application.domain.model.PlayerName;

import java.util.Optional;

public interface GameQueryPort {

    Game addGame(Game game);
    Game applyGameChanges(Game game);

    Optional<Game> loadGame(PlayerName name);
    Optional<Game> loadGame(GameID id);

}
