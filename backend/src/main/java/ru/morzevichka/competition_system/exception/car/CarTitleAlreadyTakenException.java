package ru.morzevichka.competition_system.exception.car;

public class CarTitleAlreadyTakenException extends CarException{
    public CarTitleAlreadyTakenException(String message) {
        super(message);
    }
}
