package game.domain.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Deck {
    private final List<Card> cards;

    private Deck(List<Card> cards){
        this.cards = Collections.unmodifiableList(cards);
    }

    public static Deck shuffle() {
        List<Card> deck = Arrays.stream(Suit.values())
                .flatMap(suit -> Arrays.stream(Value.values())
                        .map(value -> new Card(suit, value)))
                .collect(Collectors.toList());

        Collections.shuffle(deck);
        return new Deck(deck);
    }

    public int size(){
        return cards.size();
    }

    public List<Card> asList() {
        return Collections.unmodifiableList(cards);
    }

}