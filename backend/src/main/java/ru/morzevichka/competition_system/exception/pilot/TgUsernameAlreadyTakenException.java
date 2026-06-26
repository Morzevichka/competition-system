package ru.morzevichka.competition_system.exception.pilot;

public class TgUsernameAlreadyTakenException extends PilotException {
    public TgUsernameAlreadyTakenException(String message) {
        super(message);
    }
}
