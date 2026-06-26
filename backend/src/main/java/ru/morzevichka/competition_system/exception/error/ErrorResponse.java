package ru.morzevichka.competition_system.exception.error;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message
) {
    public static ErrorResponse create(RuntimeException exception, HttpStatus status) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(exception.getMessage()).build();
    }

    public static ResponseEntity<ErrorResponse> buildResponse(RuntimeException exception, HttpStatus status) {
        return ResponseEntity
                .status(status.value())
                .body(ErrorResponse.create(exception, status));
    }
}
