package ru.morzevichka.competition_system.exception.auth;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.morzevichka.competition_system.exception.error.ErrorResponse;

@RestControllerAdvice
public class AuthenticationExceptionHandler {

    @ExceptionHandler(JwtTypeException.class)
    public ResponseEntity<ErrorResponse> jwtTypeHandler(JwtTypeException e) {
        return ErrorResponse.buildResponse(e, HttpStatus.UNAUTHORIZED);
    }
}
