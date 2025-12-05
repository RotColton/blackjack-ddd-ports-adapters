package game.domain.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

public class Deck {
    private final LinkedHashSet<Card> cards;

    private Deck(LinkedHashSet<Card> cards){
        this.cards = cards;
    }

    public static Deck shuffle() {
        List<Card> cards = Arrays.stream(Suit.values())
                .flatMap(suit -> Arrays.stream(Value.values())
                        .map(value -> new Card(suit, value)))
                .collect(Collectors.toList());

        Collections.shuffle(cards);

        LinkedHashSet<Card> deck = new LinkedHashSet<>(cards);

        return new Deck(deck);
    }

    public int size(){
        return cards.size();
    }

    public LinkedHashSet<Card> asLinkedHashSet() {
        return cards;
    }

    public Card drawCard() {
        Card card = cards.iterator().next();
        cards.remove(card);
        return card;
    }

}