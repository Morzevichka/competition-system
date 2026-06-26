package ru.morzevichka.competition_system.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.morzevichka.competition_system.dto.car.CreateCarRequest;
import ru.morzevichka.competition_system.dto.car.UpdateCarRequest;
import ru.morzevichka.competition_system.exception.car.CarNotFoundException;
import ru.morzevichka.competition_system.exception.car.CarTitleAlreadyTakenException;
import ru.morzevichka.competition_system.model.Car;
import ru.morzevichka.competition_system.repository.CarRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public Car findById(UUID id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Машины не существует"));
    }

    public Page<Car> findCars(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    public Car createCar(CreateCarRequest body) {
        if (carRepository.existsByTitle(body.title())) {
            throw new CarTitleAlreadyTakenException("Машина с таким именем уже существует");
        }

        Car car = Car.builder().title(body.title()).build();

        return carRepository.save(car);
    }

    @Transactional
    public Car updateCar(UUID id, UpdateCarRequest body) {
        if (carRepository.existsByTitle(body.title())) {
            throw new CarTitleAlreadyTakenException("Машина с таким именем уже существует");
        }

        Car car = findById(id);
        car.setTitle(body.title());
        return car;
    }

    public void deleteCar(UUID id) {
        Car car = findById(id);
        carRepository.deleteById(id);
    }
}
