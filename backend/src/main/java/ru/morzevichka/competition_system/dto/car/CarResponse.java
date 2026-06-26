package ru.morzevichka.competition_system.dto.car;

import lombok.Builder;
import ru.morzevichka.competition_system.model.Car;

import java.util.UUID;

public record CarResponse(
        UUID id,
        String title
) {
    public static CarResponse fromEntity(Car car) {
        return new CarResponse(car.getId(), car.getTitle());
    }
}
