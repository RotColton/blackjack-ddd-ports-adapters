package game.infrastructure.adapter.in.rest.mapper;

import game.application.domain.model.Game;
import game.infrastructure.adapter.in.rest.response.StartGameResponse;
import org.springframework.stereotype.Component;

@Component
public class StartGameRestMapper {

    public StartGameResponse toResponse(Game game){

        return new StartGameResponse(
                game.id(),
                game.deck(),
                game.playerName(),
                game.playerHand(),
                game.dealerUpcard()
        );
    }
}
