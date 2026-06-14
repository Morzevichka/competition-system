package ru.morzevichka.competition_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.morzevichka.competition_system.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String username);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);
}