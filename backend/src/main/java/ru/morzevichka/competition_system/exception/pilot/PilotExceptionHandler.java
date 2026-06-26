package ru.morzevichka.competition_system.exception.pilot;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.morzevichka.competition_system.exception.error.ErrorResponse;

@RestControllerAdvice
public class PilotExceptionHandler {

    @ExceptionHandler({
            NicknameAlreadyTakenException.class,
            TgUsernameAlreadyTakenException.class
    })
    public ResponseEntity<ErrorResponse> alreadyTakenExceptionHandler(PilotException exception) {
        return ErrorResponse.buildResponse(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PilotNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundHandler(PilotNotFoundException exception) {
        return ErrorResponse.buildResponse(exception, HttpStatus.NOT_FOUND);
    }
}
