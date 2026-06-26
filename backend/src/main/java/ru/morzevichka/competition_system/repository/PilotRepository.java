package ru.morzevichka.competition_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.morzevichka.competition_system.model.Pilot;

import java.util.UUID;

public interface PilotRepository extends JpaRepository<Pilot, UUID> {

    boolean existsByNickname(String nickname);

    boolean existsByTgUsername(String tgUsername);
}
