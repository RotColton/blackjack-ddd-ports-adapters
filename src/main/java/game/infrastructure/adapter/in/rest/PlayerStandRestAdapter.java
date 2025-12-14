package game.infrastructure.adapter.in.rest;

import game.application.domain.model.Game;
import game.application.domain.model.GameID;
import game.application.in.PlayerStandCommand;
import game.application.in.PlayerStandUseCase;
import game.infrastructure.adapter.in.rest.mapper.GameRestMapper;
import game.infrastructure.adapter.in.rest.response.GameResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/games")
public class PlayerStandRestAdapter {

    private final PlayerStandUseCase useCase;

    public PlayerStandRestAdapter(PlayerStandUseCase useCase) {
        this.useCase = useCase;
    }

    @PutMapping("/stand")
    ResponseEntity<GameResponse> stand(
            @Valid @RequestParam UUID gameId
            ){
        Game game = useCase.stand(
                new PlayerStandCommand(new GameID(gameId)));

        return ResponseEntity.status(HttpStatus.OK).body(GameRestMapper.toResolvedGameResponse(game));

    }



}
