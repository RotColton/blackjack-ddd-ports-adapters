package game.application.in;

import game.application.domain.model.Game;

public interface PlayerHitUseCase {

    Game hit(PlayerHitCommand command);
}
