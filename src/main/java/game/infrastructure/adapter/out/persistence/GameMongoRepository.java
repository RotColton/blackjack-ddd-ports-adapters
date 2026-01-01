package game.infrastructure.adapter.out.persistence;

import game.application.domain.model.GameStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.Optional;

public interface GameMongoRepository extends MongoRepository<GameDocument, String> {

    Optional<GameDocument> findByPlayerNameAndStatus(String playerName, GameStatus status);


}
