package game.application.out;

import game.application.domain.model.Game;

public interface SaveGamePort {

    Game save(Game game);

}
