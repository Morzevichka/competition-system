package ru.morzevichka.competition_system.dto.pilot;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import ru.morzevichka.competition_system.util.validation.Login;
import ru.morzevichka.competition_system.util.validation.LastFirstName;
import ru.morzevichka.competition_system.util.validation.ValidationMessages;
import ru.morzevichka.competition_system.util.validation.ValidationPatterns;

import java.util.UUID;

public record UpdatePilotRequest(
        @NotNull
        UUID id,

        @Login
        String nickname,

        @LastFirstName
        @JsonProperty("first_name")
        String firstName,

        @LastFirstName
        @JsonProperty("last_name")
        String lastName,

        @Pattern(
                regexp = ValidationPatterns.TELEGRAM_PATTERN,
                message = ValidationMessages.TelegramUsername.INVALID
        )
        @JsonProperty("tg_username")
        String tgUsername
) {

}
