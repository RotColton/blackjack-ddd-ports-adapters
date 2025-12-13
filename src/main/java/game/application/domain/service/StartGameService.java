package game.application.domain.service;

import game.application.in.StartGameCommand;
import game.application.in.StartGameUseCase;
import game.application.domain.model.Game;
import game.application.out.GameSaverPort;
import game.application.out.GetGameByPlayerName;
import org.springframework.stereotype.Service;


@Service
public class StartGameService implements StartGameUseCase {

    private final GameSaverPort gameSaver;
    private final GetGameByPlayerName getGamePort;

    public StartGameService(GameSaverPort gameSaver, GetGameByPlayerName getGamePort) {
        this.gameSaver = gameSaver;
        this.getGamePort = getGamePort;
    }

    @Override
    public Game startGame(StartGameCommand command) {

        return getGamePort.getGameByPlayerName(command.playerName())
                .orElseGet(() -> gameSaver.save(Game.start(command.playerName())));

    }
}
