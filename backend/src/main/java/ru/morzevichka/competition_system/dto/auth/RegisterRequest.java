package ru.morzevichka.competition_system.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import ru.morzevichka.competition_system.util.validation.*;

public record RegisterRequest(
        @NotBlank(message = ValidationMessages.Email.NOT_BLANK)
        @Email(message = ValidationMessages.Email.INVALID)
        String email,

        @Login
        String login,

        @LastFirstName
        @JsonProperty("first_name")
        String firstName,

        @LastFirstName
        @JsonProperty("last_name")
        String lastName,

        @Password
        String password
) {
}
