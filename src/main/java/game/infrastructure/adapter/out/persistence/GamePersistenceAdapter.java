package game.infrastructure.adapter.out.persistence;

import game.application.domain.model.*;
import game.application.out.SaveGamePort;
import game.application.out.SearchGamePort;
import game.infrastructure.adapter.out.persistence.mapper.GamePersistenceMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.UUID;

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
                .map(document -> Game.from(
                        UUID.fromString(document.getId()),
                        PlayerName.of(document.getPlayerName()),
                        Deck.from(new LinkedHashSet<>(Optional.ofNullable(document.getDeck()).orElse(Collections.emptyList()))),
                        Hand.from(new LinkedHashSet<>(Optional.ofNullable(document.getPlayerHand()).orElse(Collections.emptyList()))),
                        Hand.from(new LinkedHashSet<>(Optional.ofNullable(document.getDealerHand()).orElse(Collections.emptyList()))),
                        document.getStatus()
                ));
    }

    @Override
    public Optional<Game> getGameById(GameID id) {
        return Optional.ofNullable(mapper.toDomain(repository.findById(id.id()).get()));
    }

}
