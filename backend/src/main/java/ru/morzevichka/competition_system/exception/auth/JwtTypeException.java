package ru.morzevichka.competition_system.exception.auth;

import io.jsonwebtoken.JwtException;

public class JwtTypeException extends JwtException {
    public JwtTypeException(String message) {
        super(message);
    }
}
