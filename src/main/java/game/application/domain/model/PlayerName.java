package game.application.domain.model;

public record PlayerName(String name) {


    public static PlayerName of(String name) {
        validate(name);
        return new PlayerName(name);
    }

    private static void validate(String name){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Player name cannot be blank");
        }
        if (name.length() < 3 || name.length() > 9) {
            throw new IllegalArgumentException("Player name must be between 3 and 20 characters");
        }
    }

    public String name() {
        return this.name;
    }
}
