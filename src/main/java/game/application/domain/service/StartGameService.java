package game.application.domain.service;

import game.application.in.StartGameCommand;
import game.application.in.StartGameUseCase;
import game.application.domain.model.Game;
import game.application.out.GameCommandPort;
import game.application.out.GameQueryPort;
import org.springframework.stereotype.Service;


@Service
public class StartGameService implements StartGameUseCase {

    private final GameCommandPort commandPort;
    private final GameQueryPort queryPort;

    public StartGameService(GameCommandPort commandPort, GameQueryPort queryPort) {
        this.commandPort = commandPort;
        this.queryPort = queryPort;
    }

    @Override
    public Game startGame(StartGameCommand command) {
        return queryPort.loadGame(command.playerName())
                .orElseGet(() -> commandPort.addGame(Game.start(command.playerName())));
    }
}
