package ru.morzevichka.competition_system.util.validation;

public final class ValidationMessages {
    private ValidationMessages() {}

    public static final class Email {
        public static final String NOT_BLANK =
                "Email обязателен";

        public static final String INVALID =
                "Некорректный формат email";
    }

    public static final class Login {
        public static final int MIN_SIZE = 3;
        public static final int MAX_SIZE = 30;

        public static final String NOT_BLANK =
                "Логин обязателен";

        public static final String INVALID =
                "Логин может содержать только латинские буквы, цифры и символ _";

        public static final String SIZE =
                "Логин должен содержать от 3 до 30 символов";
    }

    public static final class Password {
        public static final int MIN_SIZE = 8;
        public static final int MAX_SIZE = 16;

        public static final String NOT_BLANK =
                "Пароль обязателен";

        public static final String INVALID =
                "Пароль может содержать только латинские буквы, цифры и символы !@#$%&_-";

        public static final String SIZE =
                "Пароль должен содержать от 8 до 16 символов";
    }

    public static final class RussianName {
        public static final int MAX_SIZE = 50;

        public static final String NOT_BLANK =
                "Имя и Фамилия обязательны";

        public static final String SIZE =
                "Имя или Фамилия не должны превышать 50 символов";
    }

    public static final class TelegramUsername {
        public static final String INVALID =
                "Некорректный Telegram username";
    }

    public static final class LetterNumber {
        public static final String INVALID = "Название должно содержать только буквы и цифры";
        public static final String NOT_BLANK = "Название не должно быть пустое";
    }
}
