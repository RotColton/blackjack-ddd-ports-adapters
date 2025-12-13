package game.application.domain.service;

import game.application.domain.exception.GameNotFoundException;
import game.application.domain.model.Game;
import game.application.domain.model.GameStatus;
import game.application.in.PlayerHitCommand;
import game.application.in.PlayerHitUseCase;
import game.application.out.GameOutputPort;
import org.springframework.stereotype.Service;

@Service
public class PlayerTurnService implements PlayerHitUseCase {

    private final GameOutputPort port;

    public PlayerTurnService(GameOutputPort port) {
        this.port = port;
    }

    @Override
    public Game hit(PlayerHitCommand command) {
        return port.getGameById(command.id())
                .map(game -> {
                    if (game.status() != GameStatus.IN_PROGRESS) {
                        throw new IllegalStateException("Cannot hit: game is not in progress");
                    }
                    Game updatedGame = game.playerHit();
                    port.update(updatedGame); // persistir cambios
                    return updatedGame;
                })
                .orElseThrow(() -> new GameNotFoundException("Game not found with id: " + command.id()));
    }


}

