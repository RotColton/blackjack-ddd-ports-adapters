package game.infrastructure.adapter.in.rest.mapper;

import game.application.domain.model.Game;
import game.infrastructure.adapter.in.rest.response.GameResponse;

import java.util.List;

public class GameRestMapper {

    public static GameResponse toUpcardGameResponse(Game game) {

        return new GameResponse(
                game.id(),
                game.playerName(),
                game.playerHand(),
                List.of(game.dealerHand().get(0)),
                game.status()
        );
    }

    public static GameResponse toResolvedGameResponse(Game game){
        return new GameResponse(
                game.id(),
                game.playerName(),
                game.playerHand(),
                game.dealerHand(),
                game.status()
        );
    }
}
