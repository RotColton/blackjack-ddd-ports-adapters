package game.infrastructure.adapter.out.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GameMongoRepository extends MongoRepository<GameDocument, String> {

    Optional<GameDocument> findByPlayerName(String playerName);

}
