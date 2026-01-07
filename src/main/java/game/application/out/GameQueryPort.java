package game.application.out;

import game.application.domain.model.Game;
import game.application.domain.model.GameID;
import game.application.domain.model.PlayerName;

import java.util.Optional;

public interface GameQueryPort {

    Optional<Game> loadGame(PlayerName name);
    Optional<Game> loadGame(GameID id);

}
