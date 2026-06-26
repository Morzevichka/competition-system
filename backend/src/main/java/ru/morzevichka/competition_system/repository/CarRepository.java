package ru.morzevichka.competition_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.morzevichka.competition_system.model.Car;

import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {
    boolean existsByTitle(String title);
}
