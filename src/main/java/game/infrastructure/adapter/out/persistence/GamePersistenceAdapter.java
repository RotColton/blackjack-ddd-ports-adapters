package game.infrastructure.adapter.out.persistence;

import game.application.domain.model.*;
import game.application.out.SaveGamePort;
import game.application.out.SearchGamePort;
import game.infrastructure.adapter.out.persistence.mapper.GamePersistenceMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class GamePersistenceAdapter implements
        SaveGamePort,
        SearchGamePort {

    private final GameMongoRepository repository;
    private final GamePersistenceMapper mapper;

    public GamePersistenceAdapter(GameMongoRepository repository, GamePersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Game save(Game game) {
        if(game.status() == GameStatus.IN_PROGRESS)
            return mapper.toDomain(repository.save(mapper.toDocument(game)));
        else return game;
    }

    @Override
    public Optional<Game> getGameByPlayerName(PlayerName name) {
        return repository.findByPlayerName(name.name())
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Game> getGameById(GameID id) {
        return repository.findById(id.id())
                .map(mapper::toDomain);
    }

}
