package game.application.domain.service;

import game.application.in.StartGameCommand;
import game.application.in.StartGameUseCase;
import game.application.domain.model.Game;
import game.application.out.GameSaverPort;
import org.springframework.stereotype.Service;

@Service
public class StartGameService implements StartGameUseCase {

    private GameSaverPort gameSaver;

    @Override
    public Game startGame(StartGameCommand command) {
        return gameSaver.save(Game.start(command.playerName()));
    }
}
