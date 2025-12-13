package game.application.in;

import game.application.domain.model.Game;

public interface StartGameUseCase {

    Game startGame(StartGameCommand command);

}
