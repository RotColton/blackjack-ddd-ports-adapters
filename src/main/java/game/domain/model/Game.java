package game.domain.model;


import javax.naming.directory.InvalidAttributeValueException;

public class Game {
    private String playerName;

    private Game(String playerName) throws InvalidAttributeValueException {
        if(playerName == null || playerName.isBlank())
            throw new InvalidAttributeValueException("Invalid Player Name");
        this.playerName = playerName;
    }

    public static Game startGame(String name) throws InvalidAttributeValueException {
        return new Game(name);
    }
}
