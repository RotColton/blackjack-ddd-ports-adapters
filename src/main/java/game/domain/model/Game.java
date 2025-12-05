package game.domain.model;


import javax.naming.directory.InvalidAttributeValueException;
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

    public static Game start(String playerName){
        Deck deck = Deck.shuffle();
        Hand playerHand = new Hand(new LinkedHashSet<Card>());
        Hand dealerHand = new Hand(new LinkedHashSet<Card>());
        Game game = new Game(PlayerName.of(playerName),deck, playerHand, dealerHand);

        game.dealDealerOpeningCards();
        game.dealPlayerOpeningCards();

        return game;
    }

    private void dealDealerOpeningCards(){
        dealerHand.addCard(deck.drawCard());
        dealerHand.addCard(deck.drawCard());
    }

    private void dealPlayerOpeningCards(){
        playerHand.addCard(deck.drawCard());
        playerHand.addCard(deck.drawCard());
    }

    public List playerHand(){
        return List.copyOf(playerHand.cards());
    }

    public List dealerHand(){
        return List.copyOf(dealerHand.cards());
    }

    public List deck(){
        return List.copyOf(deck.asLinkedHashSet());
    }


}
