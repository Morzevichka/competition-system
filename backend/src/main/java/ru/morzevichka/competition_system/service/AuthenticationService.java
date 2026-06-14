package ru.morzevichka.competition_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.morzevichka.competition_system.dto.internal.AuthResult;
import ru.morzevichka.competition_system.dto.user.UserInfoResponse;
import ru.morzevichka.competition_system.exception.auth.TokenExpirationException;
import ru.morzevichka.competition_system.exception.user.*;
import ru.morzevichka.competition_system.model.User;
import ru.morzevichka.competition_system.model.UserRole;
import ru.morzevichka.competition_system.repository.UserRepository;
import ru.morzevichka.competition_system.security.CustomUserDetails;
import ru.morzevichka.competition_system.security.JwtProvider;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;

    public AuthResult login(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        email,
                        password
                )
        );

        CustomUserDetails customUserDetails =
                (CustomUserDetails) authentication.getPrincipal();

        User user = customUserDetails.getUser();

        return createAuthResult(user);
    }

    public AuthResult register(String email, String login, String firstName, String lastName, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("Пользователь существует");
        }

        if (userRepository.existsByLogin(login)) {
            throw new UserLoginAlreadyTakenException("Имя занято");
        }

        User user = User.builder()
                .email(email)
                .login(login)
                .firstName(firstName)
                .lastName(lastName)
                .role(UserRole.USER)
                .passwordHash(passwordEncoder.encode(password))
                .build();

        user = userRepository.save(user);

        return createAuthResult(user);
    }

    public AuthResult refresh(String token) {
        if (!jwtProvider.isRefreshTokenValid(token)) {
            throw new TokenExpirationException("Token Expired");
        }
        String username = jwtProvider.extractUsername(token);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        String accessToken = jwtProvider.generateToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(username);

        return new AuthResult(accessToken, refreshToken, null);
    }

    private AuthResult createAuthResult(User user) {
        String accessToken = jwtProvider.generateToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user.getEmail());

        UserInfoResponse userInfoResponse = UserInfoResponse.builder()
                .email(user.getEmail())
                .login(user.getLogin())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();

        return new AuthResult(accessToken, refreshToken, userInfoResponse);
    }
}
