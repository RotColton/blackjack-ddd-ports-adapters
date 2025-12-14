package game.application.in;

import game.application.domain.model.Game;

public interface PlayerStandUseCase {
    Game stand(PlayerStandCommand command);
}
