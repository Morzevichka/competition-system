package ru.morzevichka.competition_system.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthLoginRequest {
    private final String email;
    private final String password;
}
