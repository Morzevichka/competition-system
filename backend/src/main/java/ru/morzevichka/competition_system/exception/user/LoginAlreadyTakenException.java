package ru.morzevichka.competition_system.exception.user;

public class LoginAlreadyTakenException extends UserException {
    public LoginAlreadyTakenException(String message) {
        super(message);
    }
}
