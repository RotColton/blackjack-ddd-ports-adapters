package game.application.domain.model;


import game.application.domain.event.PlayerWinsEvent;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.UUID;


public class Game extends AbstractAggregateRoot<Game> {

    private final UUID id;
    private final PlayerName playerName;
    private final Deck deck;
    private final Hand playerHand;
    private final Hand dealerHand;
    private GameStatus status;

    private Game(
            UUID id,
            PlayerName playerName,
            Deck deck,
            Hand playerHand,
            Hand dealerHand,
            GameStatus status
    ) {
        this.id = id;
        this.playerName = playerName;
        this.deck = deck;
        this.playerHand = playerHand;
        this.dealerHand = dealerHand;
        this.status = status;
    }

    public static Game start(PlayerName playerName) {

        Deck deck = Deck.shuffle();

        Hand playerHand = new Hand(new LinkedHashSet<Card>());
        Hand dealerHand = new Hand(new LinkedHashSet<Card>());

        Game game = new Game(UUID.randomUUID(),
                playerName,
                deck,
                playerHand,
                dealerHand,
                GameStatus.IN_PROGRESS);

        game.dealOpeningCards();

        if(game.hasPlayerBlackJack()) game.playerWins();

        return game;
    }

    private void dealDealerCards() {
        dealerHand.addCard(deck.drawCard());
    }

    private void dealPlayerCards() {
        playerHand.addCard(deck.drawCard());
    }

    private void dealOpeningCards() {
        dealPlayerCards();
        dealDealerCards();
        dealPlayerCards();
        dealDealerCards();
    }

    public UUID id() {
        return this.id;
    }

    public PlayerName playerName() {
        return this.playerName;
    }

    public List<Card> playerHand() {
        return List.copyOf(playerHand.cards());
    }

    public List<Card> dealerHand() {
        return List.copyOf(dealerHand.cards());
    }

    public List<Card> deck() {
        return List.copyOf(deck.asList());
    }

    public Card dealerUpcard() {
        return dealerHand.cards().stream().findFirst().orElse(null);
    }

    public GameStatus status() {
        return this.status;
    }

    public static Game from(
            UUID id,
            PlayerName playerName,
            Deck deck,
            Hand playerHand,
            Hand dealerHand,
            GameStatus status) {

        return new Game(
                id,
                playerName,
                deck,
                playerHand,
                dealerHand,
                status);
    }

    public boolean hasPlayerBlackJack() {
        return playerHand.isBlackJack();
    }

    public Collection<Object> events(){
        return List.copyOf(super.domainEvents());
    }

    public Game playerHit(){

        if(status != GameStatus.IN_PROGRESS || playerHand.score() >= 21) return this;

        dealPlayerCards();

        if(hasPlayerBlackJack()) playerWins();

        return this;
    }

    private void playerWins(){
        registerEvent(new PlayerWinsEvent(
                id(),
                playerName().name(),
                playerHand(),
                playerHand.score()));

        status = GameStatus.PLAYER_WON;

    }

}
