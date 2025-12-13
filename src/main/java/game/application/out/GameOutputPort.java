package game.application.out;

import game.application.domain.model.Game;
import game.application.domain.model.GameID;
import game.application.domain.model.PlayerName;

import java.util.Optional;

public interface GameOutputPort {

    Game save(Game game);
    Game update(Game game);

    Optional<Game> getGameByPlayerName(PlayerName name);
    Optional<Game> getGameById(GameID id);

}
