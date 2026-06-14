package ru.morzevichka.competition_system.exception.user;

public class UserLoginAlreadyTakenException extends UserException {
    public UserLoginAlreadyTakenException(String message) {
        super(message);
    }
}
