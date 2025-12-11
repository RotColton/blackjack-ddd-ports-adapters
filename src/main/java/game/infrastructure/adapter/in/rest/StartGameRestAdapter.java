package game.infrastructure.adapter.in.rest;

import game.application.domain.model.Game;
import game.application.domain.model.PlayerName;
import game.application.in.StartGameCommand;
import game.application.in.StartGameUseCase;
import game.infrastructure.adapter.in.rest.mapper.StartGameRestMapper;
import game.infrastructure.adapter.in.rest.request.StartGameRequest;
import game.infrastructure.adapter.in.rest.response.StartGameResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StartGameRestAdapter {

    private final StartGameUseCase startGameUseCase;
    private final StartGameRestMapper mapper;

    StartGameRestAdapter(StartGameUseCase startGameUseCase, StartGameRestMapper mapper){
        this.startGameUseCase = startGameUseCase;
        this.mapper = mapper;
    }

    @PostMapping("games/start")
    public ResponseEntity<StartGameResponse> startGame(
            @RequestBody StartGameRequest request) {

        Game game = startGameUseCase.startGame(
                new StartGameCommand(PlayerName.of(request.playerName())));

        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(game));

    }


}
