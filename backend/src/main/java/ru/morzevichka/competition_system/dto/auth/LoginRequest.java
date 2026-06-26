package ru.morzevichka.competition_system.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import ru.morzevichka.competition_system.util.validation.Password;
import ru.morzevichka.competition_system.util.validation.ValidationMessages;

public record LoginRequest(
        @NotBlank(message = ValidationMessages.Email.NOT_BLANK)
        @Email(message = ValidationMessages.Email.INVALID)
        String email,

        @Password
        String password
) {}
