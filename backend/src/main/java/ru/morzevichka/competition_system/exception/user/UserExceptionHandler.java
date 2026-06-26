package ru.morzevichka.competition_system.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.morzevichka.competition_system.exception.error.ErrorResponse;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler({
            UserAlreadyExistsException.class,
            LoginAlreadyTakenException.class
    })
    public ResponseEntity<ErrorResponse> alreadyExistsHandler(UserException exception) {
        return ErrorResponse.buildResponse(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<ErrorResponse> badRequestHandler(PasswordNotMatchException exception) {
        return ErrorResponse.buildResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundHandler(UserNotFoundException exception) {
        return ErrorResponse.buildResponse(exception, HttpStatus.NOT_FOUND);
    }
}
