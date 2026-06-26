package ru.morzevichka.competition_system.dto.pilot;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Pattern;
import ru.morzevichka.competition_system.util.validation.Login;
import ru.morzevichka.competition_system.util.validation.LastFirstName;
import ru.morzevichka.competition_system.util.validation.ValidationMessages;
import ru.morzevichka.competition_system.util.validation.ValidationPatterns;

public record CreatePilotRequest(
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
) {}
