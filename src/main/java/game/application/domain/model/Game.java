package game.application.domain.model;

import java.util.LinkedHashSet;
import java.util.List;

public class Game {
    private final PlayerName playerName;
    private final Deck deck;
    private Hand playerHand;
    private Hand dealerHand;


    private Game(PlayerName playerName, Deck deck, Hand playerHand, Hand dealerHand ) {
        this.playerName = playerName;
        this.deck = deck;
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
    }

    public static Game start(PlayerName playerName){
        Deck deck = Deck.shuffle();
        Hand playerHand = new Hand(new LinkedHashSet<Card>());
        Hand dealerHand = new Hand(new LinkedHashSet<Card>());
        Game game = new Game(playerName,deck, playerHand, dealerHand);

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

}
