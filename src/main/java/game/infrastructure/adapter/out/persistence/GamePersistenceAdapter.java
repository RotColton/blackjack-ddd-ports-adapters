package game.infrastructure.adapter.out.persistence;

import game.application.domain.model.*;
import game.application.out.GameOutputPort;
import game.infrastructure.adapter.out.persistence.mapper.GamePersistenceMapper;
import org.springframework.stereotype.Component;
import java.util.Optional;

import static game.infrastructure.adapter.out.persistence.mapper.GamePersistenceMapper.*;


@Component
public class GamePersistenceAdapter implements GameOutputPort {

    private final GameMongoRepository repository;

    public GamePersistenceAdapter(GameMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Game save(Game game) {
        return toDomain(repository.save(toDocument(game)));
    }

    @Override
    public Game update(Game game) {
        return save(game);
    }

    @Override
    public Optional<Game> getGameByPlayerName(PlayerName playerName) {
        return repository.findByPlayerName(playerName.name())
                .map(GamePersistenceMapper::toDomain);
    }

    @Override
    public Optional<Game> getGameById(GameID gameID) {
        return repository.findById(gameID.id().toString())
                .map(GamePersistenceMapper::toDomain);
    }

}
