package ru.morzevichka.competition_system.exception.pilot;

public class NicknameAlreadyTakenException extends PilotException {
    public NicknameAlreadyTakenException(String message) {
        super(message);
    }
}
