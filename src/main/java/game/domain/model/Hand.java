package game.domain.model;

import java.util.LinkedHashSet;

public record Hand(LinkedHashSet<Card> cards) {

    public void addCard(Card card){
        cards.add(card);
    }

}
