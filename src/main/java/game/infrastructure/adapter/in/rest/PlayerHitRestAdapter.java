package game.infrastructure.adapter.in.rest;

import game.application.domain.model.Game;
import game.application.domain.model.GameID;
import game.application.domain.model.GameStatus;
import game.application.in.PlayerHitCommand;
import game.application.in.PlayerHitUseCase;
import game.infrastructure.adapter.in.rest.mapper.GameRestMapper;
import game.infrastructure.adapter.in.rest.response.GameResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/games")
public class PlayerHitRestAdapter {

    private final PlayerHitUseCase useCase;

    public PlayerHitRestAdapter(PlayerHitUseCase useCase) {
        this.useCase = useCase;
    }

    @PutMapping("/hit")
    public ResponseEntity<GameResponse> hit(
            @Valid @RequestParam UUID gameId
            ){
        Game game = useCase.hit(
                new PlayerHitCommand(new GameID(gameId)));

        GameResponse response = game.status() == GameStatus.IN_PROGRESS
                ? GameRestMapper.toUpcardGameResponse(game)
                : GameRestMapper.toResolvedGameResponse(game);

        return ResponseEntity.ok(response);

    }
}
