package game.infrastructure.adapter.out.persistence;

import game.application.domain.model.Deck;
import game.application.domain.model.Game;
import game.application.domain.model.Hand;
import game.application.domain.model.PlayerName;
import game.application.out.GameSaverPort;
import game.application.out.GetGameByPlayerName;
import game.infrastructure.adapter.out.persistence.mapper.GamePersistenceMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;

@Component
public class GamePersistenceAdapter implements
        GameSaverPort,
        GetGameByPlayerName {

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
    public Optional<Game> getGameByPlayerName(PlayerName name) {
        return repository.findByPlayerName(name.name())
                .map(document -> Game.from(
                        document.getId(),
                        PlayerName.of(document.getPlayerName()),
                        Deck.from(new LinkedHashSet<>(Optional.ofNullable(document.getDeck()).orElse(Collections.emptyList()))),
                        Hand.from(new LinkedHashSet<>(Optional.ofNullable(document.getPlayerHand()).orElse(Collections.emptyList()))),
                        Hand.from(new LinkedHashSet<>(Optional.ofNullable(document.getDealerHand()).orElse(Collections.emptyList()))),
                        document.getStatus()
                ));
    }

}
