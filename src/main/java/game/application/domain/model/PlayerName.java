package game.application.domain.model;

public record PlayerName(String name) {

    public static final String PLAYER_NAME_CANNOT_BE_BLANK = "Player name cannot be blank";
    public static final String PLAYER_NAME_SIZE_CONSTRAINT = "Player name must be between 3 and 9 characters";

    private static void validate(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(PLAYER_NAME_CANNOT_BE_BLANK);
        }
        if (name.length() < 3 || name.length() > 9) {
            throw new IllegalArgumentException(PLAYER_NAME_SIZE_CONSTRAINT);
        }
    }

    public String name() {
        return this.name;
    }

    public static PlayerName of(String name) {
        validate(name);
        return new PlayerName(name);
    }

}
