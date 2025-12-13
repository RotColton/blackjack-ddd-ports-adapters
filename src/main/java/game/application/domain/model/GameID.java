package game.application.domain.model;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record GameID(
        @NotBlank
        UUID id
) {}
