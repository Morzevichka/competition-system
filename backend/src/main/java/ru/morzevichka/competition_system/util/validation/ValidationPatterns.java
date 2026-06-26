package ru.morzevichka.competition_system.util.validation;

public final class ValidationPatterns {

    private ValidationPatterns() {}

    public static final String LOGIN_PATTERN = "^[A-Za-z][A-Za-z0-9._-]*[A-Za-z0-9]$";

    public static final String PASSWORD_PATTERN = "^[A-Za-z0-9!@#$%&_\\-]+$";

    public static final String TELEGRAM_PATTERN = "^[a-zA-Z][a-zA-Z0-9_]{1,30}[a-zA-Z0-9]$";

    public static final String LETTER_NUMBER_PATTERN = "^[a-zA-Zа-яёА-ЯЁ0-9]+$";
}