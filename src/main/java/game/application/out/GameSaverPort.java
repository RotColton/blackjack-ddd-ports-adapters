package game.application.out;

import game.application.domain.model.Game;

public interface GameSaverPort {

    Game save(Game game);

}
