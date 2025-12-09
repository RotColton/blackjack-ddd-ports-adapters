import game.application.domain.model.Card;
import game.application.domain.model.Deck;
import game.application.domain.model.Suit;
import game.application.domain.model.Value;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShuffledDeckTest {

    @Test
    void shouldContainAll52Cards() {
        Set<Card> deckCards = new HashSet<>(Deck.shuffle().asList());

        Set<Card> expectedCards = Arrays.stream(Suit.values())
                .flatMap(suit -> Arrays.stream(Value.values())
                        .map(value -> new Card(suit, value)))
                .collect(Collectors.toSet());

        assertEquals(52, deckCards.size(), "Deck should contain 52 cards");
        assertEquals(expectedCards, deckCards, "Deck should contain all possible cards without duplicates");
    }

    @Test
    void shouldReturnDifferentOrderAfterShuffle() {
        List<Card> firstDeck = Deck.shuffle().asList();
        List<Card> secondDeck = Deck.shuffle().asList();

        boolean isDifferent = IntStream.range(0, firstDeck.size())
                .anyMatch(i -> !firstDeck.get(i).equals(secondDeck.get(i)));

        assertTrue(isDifferent, "Shuffling should produce a different card order");
    }


}
