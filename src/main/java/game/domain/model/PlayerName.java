package game.domain.model;

public class PlayerName {
    private String value;

    private PlayerName(String value){
        this.value = value;
    }

    public static PlayerName of(String value) {
        validate(value);
        return new PlayerName(value);
    }

    private static void validate(String value){
        if(value == null || value.isBlank()){
            throw new IllegalArgumentException("Player name cannot be blank");
        }
        if (value.length() < 3 || value.length() > 9) {
            throw new IllegalArgumentException("Player name must be between 3 and 20 characters");
        }
    }
}
