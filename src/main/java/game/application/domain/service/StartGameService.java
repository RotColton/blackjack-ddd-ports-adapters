package game.application.domain.service;

import game.application.in.StartGameCommand;
import game.application.in.StartGameUseCase;
import game.application.domain.model.Game;
import game.application.out.GameOutputPort;
import org.springframework.stereotype.Service;


@Service
public class StartGameService implements StartGameUseCase {

    private  GameOutputPort port;

    public StartGameService(GameOutputPort port) {
        this.port = port;
    }

    @Override
    public Game startGame(StartGameCommand command) {
        return port.getGameByPlayerName(command.playerName())
                .orElseGet(() -> port.save(Game.start(command.playerName())));
    }
}
