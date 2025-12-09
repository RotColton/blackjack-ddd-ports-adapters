import game.application.domain.model.Card;
import game.application.domain.model.Hand;
import game.application.domain.model.Suit;
import game.application.domain.model.Value;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.LinkedHashSet;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HandWithBlackJackTest {

    static Stream<Arguments> blackJackHandsWithTwoCards() {
        return Stream.of(
                Arguments.of(Value.ACE, Value.TEN),
                Arguments.of(Value.ACE, Value.JACK),
                Arguments.of(Value.ACE, Value.QUEEN),
                Arguments.of(Value.ACE, Value.KING)
        );
    }

    static Stream<Arguments> blackJackHandsWithThreeCards() {
        return Stream.of(
                Arguments.of(Value.ACE,  Value.ACE,  Value.NINE),
                Arguments.of(Value.ACE,  Value.TWO,  Value.EIGHT),
                Arguments.of(Value.ACE,  Value.THREE, Value.SEVEN),
                Arguments.of(Value.ACE,  Value.FOUR, Value.SIX),
                Arguments.of(Value.ACE,  Value.FIVE, Value.FIVE),
                Arguments.of(Value.ACE,  Value.TEN,  Value.TEN),
                Arguments.of(Value.TWO,  Value.NINE, Value.TEN),
                Arguments.of(Value.THREE, Value.EIGHT, Value.TEN),
                Arguments.of(Value.THREE, Value.NINE,  Value.NINE),
                Arguments.of(Value.FOUR, Value.SEVEN, Value.TEN),
                Arguments.of(Value.FOUR, Value.EIGHT, Value.NINE),
                Arguments.of(Value.FIVE, Value.SIX,  Value.TEN),
                Arguments.of(Value.FIVE, Value.SEVEN, Value.NINE),
                Arguments.of(Value.FIVE, Value.EIGHT, Value.EIGHT),
                Arguments.of(Value.SIX,  Value.SIX,  Value.NINE),
                Arguments.of(Value.SIX,  Value.SEVEN, Value.EIGHT),
                Arguments.of(Value.SEVEN, Value.SEVEN, Value.SEVEN)
        );
    }

    static Stream<Arguments> blackJackHandsWithFourCards() {
        return Stream.of(
                Arguments.of(Value.TWO, Value.TWO, Value.SEVEN, Value.TEN),
                Arguments.of(Value.TWO, Value.TWO, Value.EIGHT, Value.NINE),
                Arguments.of(Value.TWO, Value.THREE, Value.SIX, Value.TEN),
                Arguments.of(Value.TWO, Value.THREE, Value.SEVEN, Value.NINE),
                Arguments.of(Value.TWO, Value.FOUR, Value.FIVE, Value.TEN),
                Arguments.of(Value.TWO, Value.FOUR, Value.SIX, Value.NINE),
                Arguments.of(Value.TWO, Value.FIVE, Value.SIX, Value.EIGHT),
                Arguments.of(Value.TWO, Value.SIX, Value.SIX, Value.SEVEN),
                Arguments.of(Value.THREE, Value.THREE, Value.FIVE, Value.TEN),
                Arguments.of(Value.THREE, Value.THREE, Value.SIX, Value.NINE),
                Arguments.of(Value.THREE, Value.FOUR, Value.FOUR, Value.TEN),
                Arguments.of(Value.THREE, Value.FOUR, Value.FIVE, Value.NINE),
                Arguments.of(Value.THREE, Value.FIVE, Value.FIVE, Value.EIGHT),
                Arguments.of(Value.FOUR, Value.FOUR, Value.THREE, Value.TEN),
                Arguments.of(Value.FOUR, Value.FOUR, Value.FOUR, Value.NINE),
                Arguments.of(Value.ACE, Value.ACE, Value.ACE, Value.EIGHT)
        );
    }


    static Stream<Arguments> blackJackHandsWithFiveCards() {
        return Stream.of(
                Arguments.of(Value.ACE, Value.ACE, Value.ACE, Value.EIGHT, Value.TEN),
                Arguments.of(Value.ACE, Value.ACE, Value.TWO, Value.SEVEN, Value.TEN),
                Arguments.of(Value.ACE, Value.ACE, Value.THREE, Value.SIX, Value.TEN),
                Arguments.of(Value.ACE, Value.ACE, Value.FOUR, Value.FIVE, Value.TEN),
                Arguments.of(Value.ACE, Value.TWO, Value.TWO, Value.SIX, Value.TEN),
                Arguments.of(Value.ACE, Value.TWO, Value.THREE, Value.FIVE, Value.TEN),
                Arguments.of(Value.TWO, Value.TWO, Value.THREE, Value.FOUR, Value.TEN)
        );
    }


    @ParameterizedTest
    @MethodSource("blackJackHandsWithTwoCards")
    void WithTwoCardsShouldHasBlackJack(Value v1, Value v2){
        Card card1 = new Card(Suit.SPADES, v1);
        Card card2 = new Card(Suit.HEARTS, v2);

        Hand hand = new Hand(new LinkedHashSet<>());
        hand.addCard(card1);
        hand.addCard(card2);

        assertTrue(hand.isBlackJack(),
                "Hand should be blackjack: " + v1 + " + " + v2);
    }

    @ParameterizedTest
    @MethodSource("blackJackHandsWithThreeCards")
    void WithThreeCardsShouldHaveBlackJack(Value v1, Value v2, Value v3) {
        Hand hand = new Hand(new LinkedHashSet<>());

        hand.addCard(new Card(Suit.SPADES, v1));
        hand.addCard(new Card(Suit.HEARTS, v2));
        hand.addCard(new Card(Suit.CLUBS, v3));

        assertTrue(hand.isBlackJack(),
                "Hand should be blackjack: " + v1 + " + " + v2 + " + " + v3);
    }

    @ParameterizedTest
    @MethodSource("blackJackHandsWithFourCards")
    void WithFourCardsShouldHaveBlackJack(Value v1, Value v2, Value v3, Value v4) {
        Hand hand = new Hand(new LinkedHashSet<>());

        hand.addCard(new Card(Suit.SPADES, v1));
        hand.addCard(new Card(Suit.HEARTS, v2));
        hand.addCard(new Card(Suit.CLUBS, v3));
        hand.addCard(new Card(Suit.DIAMONDS, v4));

        assertTrue(hand.isBlackJack(),
                "Hand should be blackjack: " + v1 + " + " + v2 + " + " + v3 + " + " + v4);
    }


    @ParameterizedTest
    @MethodSource("blackJackHandsWithFiveCards")
    void WithFiveCardsShouldHaveBlackJack(Value v1, Value v2, Value v3, Value v4, Value v5) {
        Hand hand = new Hand(new LinkedHashSet<>());

        hand.addCard(new Card(Suit.SPADES, v1));
        hand.addCard(new Card(Suit.HEARTS, v2));
        hand.addCard(new Card(Suit.CLUBS, v3));
        hand.addCard(new Card(Suit.DIAMONDS, v4));
        hand.addCard(new Card(Suit.SPADES, v5));

        assertTrue(hand.isBlackJack(),
                "Hand should be blackjack: " + v1 + " + " + v2 + " + " + v3 + " + " + v4 + " + " + v5);
    }

}
