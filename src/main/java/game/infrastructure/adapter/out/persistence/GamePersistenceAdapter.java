package game.infrastructure.adapter.out.persistence;

import game.application.domain.model.*;
import game.application.out.GameQueryPort;
import game.infrastructure.adapter.out.persistence.mapper.GamePersistenceMapper;
import org.springframework.stereotype.Component;
import java.util.Optional;

import static game.infrastructure.adapter.out.persistence.mapper.GamePersistenceMapper.*;


@Component
public class GamePersistenceAdapter implements GameQueryPort {

    private final GameMongoRepository repository;

    public GamePersistenceAdapter(GameMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public Game addGame(Game game) {
        return toDomain(repository.save(toDocument(game)));
    }

    @Override
    public Game applyGameChanges(Game game) { return toDomain(repository.save(toDocument(game))); }

    @Override
    public Optional<Game> loadGame(PlayerName playerName) {
        return repository.findByPlayerName(playerName.name())
                .map(GamePersistenceMapper::toDomain);
    }

    @Override
    public Optional<Game> loadGame(GameID gameID) {
        return repository.findById(gameID.id().toString())
                .map(GamePersistenceMapper::toDomain);
    }

}
