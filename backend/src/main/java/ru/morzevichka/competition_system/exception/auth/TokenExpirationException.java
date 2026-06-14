package ru.morzevichka.competition_system.exception.auth;

public class TokenExpirationException extends AuthenticationException{
    public TokenExpirationException(String message) {
        super(message);
    }
}
