package ru.morzevichka.competition_system.dto.internal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import ru.morzevichka.competition_system.dto.user.UserInfoResponse;

@Builder
@Getter
@AllArgsConstructor
public class AuthResult {
    private final String accessToken;
    private final String refreshToken;
    private final UserInfoResponse user;
}
