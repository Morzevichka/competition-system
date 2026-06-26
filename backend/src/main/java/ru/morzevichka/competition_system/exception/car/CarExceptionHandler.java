package ru.morzevichka.competition_system.exception.car;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.morzevichka.competition_system.exception.error.ErrorResponse;

@RestControllerAdvice
public class CarExceptionHandler {

    @ExceptionHandler(CarTitleAlreadyTakenException.class)
    public ResponseEntity<ErrorResponse> alreadyTakenHandler(CarTitleAlreadyTakenException exception) {
        return ErrorResponse.buildResponse(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CarNotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundHandler(CarNotFoundException exception) {
        return ErrorResponse.buildResponse(exception, HttpStatus.NOT_FOUND);
    }
}
