package game.application.domain.service;

import game.application.in.StartGameCommand;
import game.application.in.StartGameUseCase;
import game.application.domain.model.Game;
import game.application.out.GameQueryPort;
import org.springframework.stereotype.Service;


@Service
public class StartGameService implements StartGameUseCase {

    private final GameQueryPort port;

    public StartGameService(GameQueryPort port) {
        this.port = port;
    }

    @Override
    public Game startGame(StartGameCommand command) {
        return port.loadGame(command.playerName())
                .orElseGet(() -> port.addGame(Game.start(command.playerName())));
    }
}
