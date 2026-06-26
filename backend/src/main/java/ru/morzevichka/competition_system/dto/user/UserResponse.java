package ru.morzevichka.competition_system.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import ru.morzevichka.competition_system.model.User;
import ru.morzevichka.competition_system.model.UserRole;

import java.util.UUID;

@Builder
public record UserResponse(
        UUID id,
        String email,
        String login,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        UserRole role
)
{
    public static UserResponse fromEntity(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .login(user.getLogin())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }
}
