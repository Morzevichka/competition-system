package ru.morzevichka.competition_system.controller.api;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.morzevichka.competition_system.dto.auth.AuthLoginRequest;
import ru.morzevichka.competition_system.dto.auth.AuthRegisterRequest;
import ru.morzevichka.competition_system.dto.internal.AuthResult;
import ru.morzevichka.competition_system.dto.user.UserInfoResponse;
import ru.morzevichka.competition_system.security.JwtProperties;
import ru.morzevichka.competition_system.service.AuthenticationService;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtProperties jwtProperties;

    @PostMapping("/login")
    public ResponseEntity<UserInfoResponse> login(
            @RequestBody AuthLoginRequest request,
            HttpServletResponse response
    ) {
        AuthResult authResult = authenticationService.login(request.getEmail(), request.getPassword());
        addAuthCookies(response, authResult);
        return ResponseEntity.ok(authResult.getUser());
    }

    @PostMapping("/register")
    public ResponseEntity<UserInfoResponse> register(
            @RequestBody AuthRegisterRequest request,
            HttpServletResponse response
    ) {
        AuthResult authResult = authenticationService.register(
                request.getEmail(),
                request.getLogin(),
                request.getFirstName(),
                request.getLastName(),
                request.getPassword()
        );
        addAuthCookies(response, authResult);
        return ResponseEntity.ok(authResult.getUser());
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refresh(
            @CookieValue("refreshToken") String refreshToken,
            HttpServletResponse response
    ) {
        AuthResult authResult = authenticationService.refresh(refreshToken);
        addAuthCookies(response, authResult);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        Cookie accessTokenCookie = new Cookie("accessToken", "");
        accessTokenCookie.setMaxAge(0);
        accessTokenCookie.setPath("/");

        Cookie refreshTokenCookie = new Cookie("refreshToken", "");
        refreshTokenCookie.setMaxAge(0);
        refreshTokenCookie.setPath("/");

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    private void addAuthCookies(
            HttpServletResponse response,
            AuthResult authResult
    ) {
        Cookie accessTokenCookie = new Cookie("accessToken", authResult.getAccessToken());
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(Math.toIntExact(jwtProperties.getAccessExpiration() / 1000));

        Cookie refreshTokenCookie = new Cookie("refreshToken", authResult.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(Math.toIntExact(jwtProperties.getRefreshExpiration() / 1000));

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }
}

