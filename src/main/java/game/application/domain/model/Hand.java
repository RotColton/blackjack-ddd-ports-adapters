package game.application.domain.model;

import java.util.LinkedHashSet;

public record Hand(
        LinkedHashSet<Card> cards
) {

    private static final short BLACKJACK = 21;

    public void addCard(Card card) {
        cards.add(card);
    }

    public int score() {
        int total = cards.stream()
                .mapToInt(card -> card.value().getPoints())
                .sum();

        long aces = cards.stream()
                .filter(c -> c.value() == Value.ACE)
                .count();

        while (aces > 0 && total + 10 <= 21) {
            total += 10;
            aces--;
        }

        return total;
    }

    public boolean isBlackJack() {
        return score() == BLACKJACK;
    }

    public static Hand from(LinkedHashSet<Card> cards) {
        return new Hand(cards);
    }
}
