package game.infrastructure.adapter.in.rest;

import game.application.domain.model.Game;
import game.application.domain.model.PlayerName;
import game.application.in.StartGameCommand;
import game.application.in.StartGameUseCase;
import game.infrastructure.adapter.in.rest.mapper.GameRestMapper;
import game.infrastructure.adapter.in.rest.request.StartGameRequest;
import game.infrastructure.adapter.in.rest.response.GameResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class StartGameRestAdapter {

    private final StartGameUseCase startGameUseCase;

    StartGameRestAdapter(StartGameUseCase startGameUseCase) {
        this.startGameUseCase = startGameUseCase;
    }

    @PostMapping("/start")
    public ResponseEntity<GameResponse> startGame(
            @Valid @RequestBody StartGameRequest request) {

        Game game = startGameUseCase.startGame(
                new StartGameCommand(PlayerName.of(request.playerName())));

        return ResponseEntity.status(HttpStatus.CREATED).body(GameRestMapper.toResponse(game));

    }


}
