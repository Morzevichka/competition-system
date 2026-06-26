package ru.morzevichka.competition_system.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import ru.morzevichka.competition_system.model.UserRole;
import ru.morzevichka.competition_system.util.validation.Login;
import ru.morzevichka.competition_system.util.validation.Password;
import ru.morzevichka.competition_system.util.validation.LastFirstName;
import ru.morzevichka.competition_system.util.validation.ValidationMessages;

@Builder
public record CreateUserRequest(
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

        UserRole role,

        @Password
        String password
) {
}
