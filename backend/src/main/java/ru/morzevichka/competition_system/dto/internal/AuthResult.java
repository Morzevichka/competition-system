package ru.morzevichka.competition_system.dto.internal;

import ru.morzevichka.competition_system.dto.user.UserMeResponse;

public record AuthResult(
        String accessToken,
        String refreshToken,
        UserMeResponse user
) {
}
