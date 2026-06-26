package ru.morzevichka.competition_system.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.morzevichka.competition_system.exception.error.ErrorValidationResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorValidationResponse> validationError(
            MethodArgumentNotValidException ex
    ) {
        return ErrorValidationResponse.buildResponse(ex);
    }
}
