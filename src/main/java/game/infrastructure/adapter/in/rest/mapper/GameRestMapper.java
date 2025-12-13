package game.infrastructure.adapter.in.rest.mapper;

import game.application.domain.model.Game;
import game.infrastructure.adapter.in.rest.response.GameResponse;
import org.springframework.stereotype.Component;

@Component
public class GameRestMapper {

    public static GameResponse toResponse(Game game) {

        return new GameResponse(
                game.id(),
                game.playerName(),
                game.playerHand(),
                game.dealerHand().get(0),
                game.status()
        );
    }
}
