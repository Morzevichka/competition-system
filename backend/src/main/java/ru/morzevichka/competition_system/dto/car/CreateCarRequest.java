package ru.morzevichka.competition_system.dto.car;

import ru.morzevichka.competition_system.util.validation.LetterNumberField;

public record CreateCarRequest(
        @LetterNumberField
        String title
) {
}
