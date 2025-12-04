package game.domain.model;


import javax.naming.directory.InvalidAttributeValueException;

public class Game {
    private final PlayerName playerName;
    private final Deck deck;

    private Game(PlayerName playerName, Deck deck) throws InvalidAttributeValueException {
        this.playerName = playerName;
        this.deck = deck;
    }

    public static Game startGame(String playerName) throws InvalidAttributeValueException {
        return new Game(PlayerName.of(playerName), Deck.shuffle());
    }
}
