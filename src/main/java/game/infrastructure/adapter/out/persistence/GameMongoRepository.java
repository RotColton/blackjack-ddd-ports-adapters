package game.infrastructure.adapter.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;
import java.util.UUID;

public interface GameMongoRepository extends MongoRepository<GameDocument, UUID> {

    Optional<GameDocument> findByPlayerName(String playerName);
}
