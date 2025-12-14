package game.application.domain.service;

import game.application.domain.exception.GameNotFoundException;
import game.application.domain.model.Game;
import game.application.in.PlayerHitCommand;
import game.application.in.PlayerHitUseCase;
import game.application.in.PlayerStandCommand;
import game.application.in.PlayerStandUseCase;
import game.application.out.GameOutputPort;
import org.springframework.stereotype.Service;

@Service
public class PlayerTurnService implements PlayerHitUseCase, PlayerStandUseCase {

    private final GameOutputPort port;

    public PlayerTurnService(GameOutputPort port) {
        this.port = port;
    }

    @Override
    public Game hit(PlayerHitCommand command) {
        return port.getGameById(command.gameID())
                .map(game -> {
                    Game updatedGame = game.playerHit();
                    port.update(updatedGame);
                    return updatedGame;
                })//todo creare message exception constant
                .orElseThrow(() -> new GameNotFoundException("Game not found"));
    }


    @Override
    public Game stand(PlayerStandCommand command) {
        return port.getGameById(command.gameID())
                .map(game ->{
                    Game updatedGame = game.playerStand();
                    port.update(updatedGame);
                    return updatedGame;
                })
                .orElseThrow(() -> new GameNotFoundException("Game not found"));
    }
}

