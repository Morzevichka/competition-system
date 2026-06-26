package ru.morzevichka.competition_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.morzevichka.competition_system.dto.user.UpdateMeRequest;
import ru.morzevichka.competition_system.dto.user.UpdateUserRequest;
import ru.morzevichka.competition_system.exception.user.LoginAlreadyTakenException;
import ru.morzevichka.competition_system.exception.user.UserAlreadyExistsException;
import ru.morzevichka.competition_system.exception.user.UserNotFoundException;
import ru.morzevichka.competition_system.model.User;
import ru.morzevichka.competition_system.model.UserRole;
import ru.morzevichka.competition_system.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Пользователь не найден"));
    }

    public User createUser(String email, String login, String firstName, String lastName, String password, UserRole role) {
        validateUniqueFields(email, login, null);

        User user = User.builder()
                .email(email)
                .login(login)
                .firstName(firstName)
                .lastName(lastName)
                .role(role)
                .passwordHash(passwordEncoder.encode(password))
                .build();

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(UUID id, UpdateUserRequest body) {
        validateUniqueFields(body.email(), body.login(), id);

        User user = findById(id);

        user.setEmail(body.email());
        user.setLogin(body.login());
        user.setFirstName(body.firstName());
        user.setLastName(body.lastName());
        user.setRole(body.role());

        return user;
    }

    public void deleteUser(UUID id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    @Transactional
    public User updateProfile(UUID id, UpdateMeRequest body) {
        validateUniqueFields(null, body.login(), id);

        User user = findById(id);

        user.setLogin(body.login());
        user.setFirstName(body.firstName());
        user.setLastName(body.lastName());

        return user;
    }

    private void validateUniqueFields(String email, String login, UUID id) {
        userRepository.findByEmail(email)
                .filter(user -> !user.getId().equals(id))
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("Пользователь уже существует");
                });

        userRepository.findByLogin(login)
                .filter(user -> !user.getId().equals(id))
                .ifPresent(user -> {
                    throw new LoginAlreadyTakenException("Login занят");
                });
    }
}