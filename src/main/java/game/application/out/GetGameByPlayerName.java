package game.application.out;

import game.application.domain.model.Game;
import game.application.domain.model.PlayerName;

import java.util.Optional;

public interface GetGameByPlayerName {

    Optional<Game> getGameByPlayerName(PlayerName name);

}
