package ru.morzevichka.competition_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.morzevichka.competition_system.dto.user.UserInfoResponse;
import ru.morzevichka.competition_system.exception.user.UserNotFoundException;
import ru.morzevichka.competition_system.model.User;
import ru.morzevichka.competition_system.repository.UserRepository;
import ru.morzevichka.competition_system.security.JwtProvider;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    public UserInfoResponse me(String token) {
        String username = jwtProvider.extractUsername(token);

        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));

        return UserInfoResponse.builder()
                .email(user.getEmail())
                .login(user.getLogin())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .build();
    }
}
