package ru.morzevichka.competition_system.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.morzevichka.competition_system.dto.car.CreateCarRequest;
import ru.morzevichka.competition_system.dto.car.CarResponse;
import ru.morzevichka.competition_system.dto.car.UpdateCarRequest;
import ru.morzevichka.competition_system.dto.external.PageResponse;
import ru.morzevichka.competition_system.model.Car;
import ru.morzevichka.competition_system.service.CarService;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<PageResponse<CarResponse>> getCars(@PageableDefault(size = 20)  Pageable pageable) {
        PageResponse<CarResponse> response = PageResponse.fromPage(carService.findCars(pageable), CarResponse::fromEntity);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CarResponse> createCar(@RequestBody CreateCarRequest body) {
        Car car = carService.createCar(body);
        return ResponseEntity
                .created(URI.create(String.format("/api/cars/%s", car.getId())))
                .body(CarResponse.fromEntity(car));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<CarResponse> updateCar(@PathVariable UUID id, @RequestBody UpdateCarRequest body) {
        Car car = carService.updateCar(id, body);
        return ResponseEntity.ok(CarResponse.fromEntity(car));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteCar(@PathVariable UUID id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}
