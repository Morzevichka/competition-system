package ru.morzevichka.competition_system.exception.error;

public record ValidationError(
        String field,
        String message
) {}
