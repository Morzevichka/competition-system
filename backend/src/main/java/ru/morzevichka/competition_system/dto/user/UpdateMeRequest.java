package ru.morzevichka.competition_system.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.morzevichka.competition_system.util.validation.LastFirstName;
import ru.morzevichka.competition_system.util.validation.Login;

public record UpdateMeRequest(
        @Login
        String login,

        @LastFirstName
        @JsonProperty("first_name")
        String firstName,

        @LastFirstName
        @JsonProperty("last_name")
        String lastName
) {
}
