package ru.morzevichka.competition_system.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ru.morzevichka.competition_system.model.UserRole;

@Getter
@Builder
@AllArgsConstructor
public class UserInfoResponse {
    private String email;
    private String login;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private UserRole role;
}
