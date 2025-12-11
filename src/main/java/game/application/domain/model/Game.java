package game.application.domain.model;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;

public class Game {
    private final UUID id;
    private final PlayerName playerName;
    private final Deck deck;
    private final Hand playerHand;
    private final Hand dealerHand;


    private Game(UUID id, PlayerName playerName, Deck deck, Hand playerHand, Hand dealerHand ) {
        this.id = id;
        this.playerName = playerName;
        this.deck = deck;
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
    }

    public static Game start(PlayerName playerName){
        Deck deck = Deck.shuffle();
        Hand playerHand = new Hand(new LinkedHashSet<Card>());
        Hand dealerHand = new Hand(new LinkedHashSet<Card>());
        Game game = new Game(UUID.randomUUID(), playerName,deck, playerHand, dealerHand);

        game.dealOpeningCards();

        return game;
    }

    private void dealDealerCards(){
        dealerHand.addCard(deck.drawCard());
    }

    private void dealPlayerCards(){
        playerHand.addCard(deck.drawCard());
    }

    private void dealOpeningCards(){
        dealPlayerCards();
        dealDealerCards();
        dealPlayerCards();
        dealDealerCards();
    }

    public UUID id(){
        return this.id;
    }

    public PlayerName playerName(){
        return this.playerName;
    }

    public List<Card> playerHand(){
        return List.copyOf(playerHand.cards());
    }

    public List<Card> dealerHand(){
        return List.copyOf(dealerHand.cards());
    }

    public List<Card> deck(){
        return List.copyOf(deck.asList());
    }

    public boolean playerHasBlackJack(){
        return playerHand.isBlackJack();
    }

    public Card dealerUpcard() {
        return dealerHand.cards().stream().findFirst().orElse(null);
    }

}
