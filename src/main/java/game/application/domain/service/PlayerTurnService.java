package game.application.domain.service;

import game.application.domain.exception.GameNotFoundException;
import game.application.domain.model.Game;
import game.application.in.PlayerHitCommand;
import game.application.in.PlayerHitUseCase;
import game.application.in.PlayerStandCommand;
import game.application.in.PlayerStandUseCase;
import game.application.out.GameCommandPort;
import game.application.out.GameQueryPort;
import org.springframework.stereotype.Service;

@Service
public class PlayerTurnService implements PlayerHitUseCase, PlayerStandUseCase {

    public static final String GAME_NOT_FOUND = "Game not found";
    private final GameQueryPort queryPort;
    private final GameCommandPort commandPort;

    public PlayerTurnService(GameQueryPort queryPort, GameCommandPort commandPort) {
        this.queryPort = queryPort;
        this.commandPort = commandPort;
    }

    @Override
    public Game hit(PlayerHitCommand command) {
        return queryPort.loadGame(command.gameID())
                .map(game -> {
                    Game updatedGame = game.playerHit();
                    commandPort.applyGameChanges(updatedGame);
                    return updatedGame;
                })
                .orElseThrow(() -> new GameNotFoundException(GAME_NOT_FOUND));
    }


    @Override
    public Game stand(PlayerStandCommand command) {
        return queryPort.loadGame(command.gameID())
                .map(game ->{
                    Game updatedGame = game.playerStand();
                    commandPort.applyGameChanges(updatedGame);
                    return updatedGame;
                })
                .orElseThrow(() -> new GameNotFoundException(GAME_NOT_FOUND));
    }
}

