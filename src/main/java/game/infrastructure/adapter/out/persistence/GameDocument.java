package game.infrastructure.adapter.out.persistence;

import game.application.domain.model.Card;
import game.application.domain.model.GameStatus;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Document(collection = "games")
public class GameDocument {
    @Id
    private UUID id;
    private List<Card> deck;
    private String playerName;
    private List<Card> playerHand;
    private List<Card> dealerHand;
    private GameStatus status;


}
