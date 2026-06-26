package ru.morzevichka.competition_system.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.morzevichka.competition_system.dto.auth.LoginRequest;
import ru.morzevichka.competition_system.dto.auth.RegisterRequest;
import ru.morzevichka.competition_system.dto.internal.AuthResult;
import ru.morzevichka.competition_system.dto.user.UserMeResponse;
import ru.morzevichka.competition_system.model.User;
import ru.morzevichka.competition_system.model.UserRole;
import ru.morzevichka.competition_system.security.CustomUserDetails;
import ru.morzevichka.competition_system.security.JwtProperties;
import ru.morzevichka.competition_system.security.JwtProvider;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;
    private final AuthenticationManager authenticationManager;

    public AuthResult login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        CustomUserDetails customUserDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User user = customUserDetails.getUser();

        return createAuthResult(user);
    }

    public AuthResult register(RegisterRequest request) {
        User user = userService.createUser(
                request.email(),
                request.login(),
                request.firstName(),
                request.lastName(),
                request.password(),
                UserRole.USER
        );
        return createAuthResult(user);
    }

    public AuthResult refresh(String token) {
        jwtProvider.isRefreshTokenValid(token);

        String username = jwtProvider.extractUsername(token);
        User user = userService.findByEmail(username);

        String accessToken = jwtProvider.generateToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(username);

        return new AuthResult(accessToken, refreshToken, null);
    }

    public void clearAuthCookie(HttpServletResponse response) {
        Cookie accessCookie = new Cookie("accessToken", "");
        accessCookie.setHttpOnly(true);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(0);

        Cookie refreshCookie = new Cookie("refreshToken", "");
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(0);

        response.addCookie(accessCookie);
        response.addCookie(refreshCookie);
    }

    public void addAuthCookie(HttpServletResponse response, String accessToken, String refreshToken) {
        Cookie accessTokenCookie = new Cookie("accessToken", accessToken);
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setSecure(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(Math.toIntExact(jwtProperties.getAccessExpiration() / 1000));

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(Math.toIntExact(jwtProperties.getRefreshExpiration() / 1000));

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    private AuthResult createAuthResult(User user) {
        String accessToken = jwtProvider.generateToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user.getEmail());

        return new AuthResult(
                accessToken,
                refreshToken,
                UserMeResponse.fromEntity(user)
        );
    }

}
