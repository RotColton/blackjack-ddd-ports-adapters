package game.infrastructure.adapter.out.persistence;

import game.application.domain.model.Card;
import game.application.domain.model.GameStatus;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;


@Getter
@Builder
@Document(collection = "games")
public class GameDocument {

    @Id
    private String id;

    private String playerName;

    private List<Card> deck;

    private List<Card> playerHand;

    private List<Card> dealerHand;

    private GameStatus status;

}
