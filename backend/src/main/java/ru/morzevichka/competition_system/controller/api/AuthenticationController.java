package ru.morzevichka.competition_system.controller.api;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.morzevichka.competition_system.dto.auth.LoginRequest;
import ru.morzevichka.competition_system.dto.auth.RegisterRequest;
import ru.morzevichka.competition_system.dto.internal.AuthResult;
import ru.morzevichka.competition_system.dto.user.UserMeResponse;
import ru.morzevichka.competition_system.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<UserMeResponse> login(
            @Valid @RequestBody LoginRequest body,
            HttpServletResponse response
    ) {
        AuthResult authResult = authenticationService.login(body);
        authenticationService.addAuthCookie(response, authResult.accessToken(), authResult.refreshToken());
        return ResponseEntity.ok(authResult.user());
    }

    @PostMapping("/register")
    public ResponseEntity<UserMeResponse> register(
            @Valid @RequestBody RegisterRequest body,
            HttpServletResponse response
    ) {
        AuthResult authResult = authenticationService.register(body);
        authenticationService.addAuthCookie(response, authResult.accessToken(), authResult.refreshToken());
        return ResponseEntity.ok(authResult.user());
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(
            @CookieValue("refreshToken") String refreshToken,
            HttpServletResponse response
    ) {
        AuthResult authResult = authenticationService.refresh(refreshToken);
        System.out.println(authResult);
        authenticationService.addAuthCookie(response, authResult.accessToken(), authResult.refreshToken());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        authenticationService.clearAuthCookie(response);
    }
}

