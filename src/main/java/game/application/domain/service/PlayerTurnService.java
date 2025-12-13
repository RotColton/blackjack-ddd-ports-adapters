package game.application.domain.service;

import game.application.domain.exception.GameNotFoundException;
import game.application.domain.model.Game;
import game.application.in.PlayerHitCommand;
import game.application.in.PlayerHitUseCase;
import game.application.out.SearchGamePort;


public class PlayerTurnService implements PlayerHitUseCase {

    private SearchGamePort port;

    @Override
    public Game hit(PlayerHitCommand command) {
        return port.getGameById(command.id())
                .map(Game::playerHit)
                .orElseThrow(() -> new GameNotFoundException("Game not found with id: " + command.id()));
    }

}

