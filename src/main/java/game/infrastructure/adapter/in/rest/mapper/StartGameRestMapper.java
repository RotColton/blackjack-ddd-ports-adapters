package game.infrastructure.adapter.in.rest.mapper;

import game.application.domain.model.Game;
import game.infrastructure.adapter.in.rest.response.StartGameResponse;
import org.springframework.stereotype.Component;

@Component
public class StartGameRestMapper {

    public static StartGameResponse toResponse(Game game) {

        return new StartGameResponse(
                game.id(),
                game.playerName(),
                game.playerHand(),
                game.dealerHand().get(0),
                game.status()
        );
    }
}
