package game.domain.model;


import javax.naming.directory.InvalidAttributeValueException;

public class Game {
    private String playerName;
    private Deck deck;

    private Game(String playerName, Deck deck) throws InvalidAttributeValueException {
        if(playerName == null || playerName.isBlank())
            throw new InvalidAttributeValueException("Invalid Player Name");
        this.playerName = playerName;
        this.deck = deck;
    }

    public static Game startGame(String name) throws InvalidAttributeValueException {
        return new Game(name, Deck.shuffle());
    }
}
