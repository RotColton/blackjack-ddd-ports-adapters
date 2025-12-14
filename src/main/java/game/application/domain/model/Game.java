package game.application.domain.model;


import game.application.domain.event.PlayerLosesEvent;
import game.application.domain.event.PlayerWinsEvent;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.util.*;


public class Game extends AbstractAggregateRoot<Game> {

    public static final String CANNOT_STAND = "Cannot stand: the game is not in progress";
    public static final String CANNOT_HIT = "Cannot hit: the game is not in progress";
    private final GameID id;
    private final PlayerName playerName;
    private final Deck deck;
    private final Hand playerHand;
    private final Hand dealerHand;
    private GameStatus status;

    private Game(
            GameID id,
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

        Game game = new Game(new GameID(UUID.randomUUID()),
                playerName,
                deck,
                playerHand,
                dealerHand,
                GameStatus.IN_PROGRESS);

        game.dealOpeningCards();

        if(game.hasPlayerBlackJack()) return game.playerWins();

        return game;
    }

    private void dealOpeningCards() {
        dealPlayerCards();
        dealDealerCards();
        dealPlayerCards();
        dealDealerCards();
    }

    private void dealDealerCards() {
        dealerHand.addCard(deck.drawCard());
    }

    private void dealPlayerCards() { playerHand.addCard(deck.drawCard()); }

    public boolean hasPlayerBlackJack() {
        return playerHand.isBlackJack();
    }

    public Game playerHit(){

        if(status != GameStatus.IN_PROGRESS)
            throw new IllegalStateException(CANNOT_HIT);

        dealPlayerCards();

        if(isPlayerBust()) return playerLoses();

        if(hasPlayerBlackJack()) return dealerHit();

        return this;

    }

    public Game dealerHit(){

        while(dealerHand.score() < 17)
            dealDealerCards();

        if (isDealerBust()){ return playerWins(); }

        return resolveGameOutcome();
    }

    public Game playerStand(){
        if(status != GameStatus.IN_PROGRESS)
            throw new IllegalStateException(CANNOT_STAND);
        return dealerHit();
    }

    public Game resolveGameOutcome(){
        if(playerHand.score() == dealerHand.score()) return endInPush();

        if(playerHand.score() > dealerHand.score()) return playerWins();

        return playerLoses();
    }

    public boolean isPlayerBust() { return playerHand.score() > 21; }

    public boolean isDealerBust() { return dealerHand.score() > 21; }

    private Game playerLoses() {
        registerEvent(new PlayerLosesEvent(
                id(),
                playerName().name(),
                playerHand(),
                playerHand.score()));

        status = GameStatus.DEALER_WON;

        return this;
    }

    private Game playerWins(){
        registerEvent(new PlayerWinsEvent(
                id(),
                playerName().name(),
                playerHand(),
                playerHand.score()));

        status = GameStatus.PLAYER_WON;

        return this;

    }

    public Game endInPush(){
        status = GameStatus.PUSH;
        return this;
    }

    public GameID id() {
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

    public GameStatus status() {
        return this.status;
    }

    public static Game from(
            GameID id,
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

    public Collection<Object> events(){
        return List.copyOf(super.domainEvents());
    }

}
