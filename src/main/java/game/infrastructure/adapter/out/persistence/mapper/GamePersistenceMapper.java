package game.infrastructure.adapter.out.persistence.mapper;

import game.application.domain.model.*;
import game.infrastructure.adapter.out.persistence.GameDocument;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;

@Component
public class GamePersistenceMapper {

    public GameDocument toDocument(Game game) {
        return GameDocument.builder()
                .id(game.id())
                .playerName(game.playerName().toString())
                .playerHand(game.playerHand())
                .dealerHand(game.dealerHand())
                .status(game.status())
                .build();
    }

    public Game toDomain(GameDocument document) {
        return Game.from(
                document.getId(),
                PlayerName.of(document.getPlayerName()),
                Deck.from(new LinkedHashSet<>((document.getDeck()))),
                Hand.from(new LinkedHashSet<>(document.getPlayerHand())),
                Hand.from(new LinkedHashSet<>(document.getDealerHand())),
                document.getStatus());

    }

}
