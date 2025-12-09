package game.application.domain.service;

import game.application.in.StartGameCommand;
import game.application.in.StartGameUseCase;
import game.application.domain.model.Game;
import game.application.domain.model.PlayerName;
import game.application.out.GameSaverPort;

public class StartGameService implements StartGameUseCase {

    private GameSaverPort gameSaver;

    @Override
    public Game startGame(StartGameCommand command) {

        PlayerName playerName = PlayerName.of(command.playerName());

        return gameSaver.save(Game.start(playerName));
    }
}
