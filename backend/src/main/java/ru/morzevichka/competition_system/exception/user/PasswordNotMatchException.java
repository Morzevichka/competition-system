package ru.morzevichka.competition_system.exception.user;

public class PasswordNotMatchException extends UserException {
    public PasswordNotMatchException(String message) {
        super(message);
    }
}
