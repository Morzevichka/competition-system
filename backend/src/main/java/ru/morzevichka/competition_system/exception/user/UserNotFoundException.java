package ru.morzevichka.competition_system.exception.user;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
