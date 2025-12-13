package game.infrastructure.adapter.out.persistence;

import game.application.domain.model.*;
import game.application.out.GameOutputPort;
import game.infrastructure.adapter.out.persistence.mapper.GamePersistenceMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class GamePersistenceAdapter implements GameOutputPort {

    private final GameMongoRepository repository;
    private final GamePersistenceMapper mapper;

    public GamePersistenceAdapter(GameMongoRepository repository, GamePersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Game save(Game game) {
        return mapper.toDomain(repository.save(mapper.toDocument(game)));
    }

    @Override
    public Game update(Game game) {
        return save(game);
    }

    @Override
    public Optional<Game> getGameByPlayerName(PlayerName name) {
        return repository.findByPlayerName(name.name())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Game> getGameById(GameID gameID) {
        return repository.findById(gameID.id().toString())
                .map(mapper::toDomain);
    }

}
