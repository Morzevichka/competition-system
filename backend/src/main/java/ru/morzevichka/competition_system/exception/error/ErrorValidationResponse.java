package ru.morzevichka.competition_system.exception.error;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ErrorValidationResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        List<ValidationError> errors
) {
    public static ErrorValidationResponse buildValidationError(MethodArgumentNotValidException ex) {
        List<ValidationError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(ErrorValidationResponse::toValidationError)
                .toList();

        return ErrorValidationResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.valueOf(400).getReasonPhrase())
                .errors(errors)
                .build();
    }

    public static ResponseEntity<ErrorValidationResponse> buildResponse(MethodArgumentNotValidException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(buildValidationError(ex));
    }

    private static ValidationError toValidationError(FieldError error) {
        return new ValidationError(
                error.getField(),
                error.getDefaultMessage()
        );
    }
}
