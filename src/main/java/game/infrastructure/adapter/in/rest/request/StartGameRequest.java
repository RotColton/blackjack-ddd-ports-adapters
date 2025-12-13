package game.infrastructure.adapter.in.rest.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record StartGameRequest(
        @NotBlank
        @Size(min = 3, max = 9)
        String playerName
) {}

