package ru.morzevichka.competition_system.dto.car;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import ru.morzevichka.competition_system.util.validation.LetterNumberField;

import java.util.UUID;

@Builder
public record CarRequest(
        @NotBlank
        UUID id,

        @LetterNumberField
        String title
) {
}
