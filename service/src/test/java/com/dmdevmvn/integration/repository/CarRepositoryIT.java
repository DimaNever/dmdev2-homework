package com.dmdevmvn.integration.repository;

import com.dmdevmvn.dao.CarRepository;
import com.dmdevmvn.entity.Car;
import com.dmdevmvn.entity.Client;
import com.dmdevmvn.integration.IntegrationTestBase;
import com.dmdevmvn.util.EntityUtil;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
public class CarRepositoryIT extends IntegrationTestBase {

    private final CarRepository carRepository;

    @Test
    void findAllCarsByClientId() {
        List<Car> carList = carRepository.findAllCarsByClientId(1L);
        assertThat(carList).hasSize(2);

        var carIds = carList.stream()
                .map(Car::getId)
                .collect(toSet());

        assertThat(carIds).containsExactlyInAnyOrder(1L, 2L);
    }

    @Test
    void findAllByModel() {
        List<Car> carList = carRepository.findAllCarsByModel("Audi");
        assertThat(carList).hasSize(2);

        var models = carList.stream().map(Car::getId).collect(toSet());
        assertThat(models).containsExactlyInAnyOrder(1L, 3L);
    }

    @Test
    void findAll() {
        var carList = carRepository.findAll();
        assertThat(carList).hasSize(5);
    }

    @Test
    void createCar() {
        Client expectedClient = EntityUtil.buildRandomClient("Save", "Car");
        Car expectedCar = EntityUtil.buildCar("Ford", expectedClient);
        var saveCar = carRepository.save(expectedCar);

        entityManager.clear();

        Assertions.assertNotNull(saveCar.getId());
    }

    @Test
    void readCar() {
        Client expectedClient = EntityUtil.buildRandomClient("Save", "Car");
        entityManager.persist(expectedClient);
        Car expectedCar = EntityUtil.buildCar("Ford", expectedClient);
        carRepository.save(expectedCar);

        entityManager.clear();

        Optional<Car> actualCar = carRepository.findById(expectedCar.getId());

        Assertions.assertTrue(actualCar.isPresent());
        assertThat(actualCar.get()).isEqualTo(expectedCar);
    }

    @Test
    void updateCar() {
        Client expectedClient = EntityUtil.buildRandomClient("Save", "Car");
        entityManager.persist(expectedClient);
        Car expectedCar = EntityUtil.buildCar("Ford", expectedClient);

        carRepository.save(expectedCar);

        expectedCar.setModel("UPDATE");
        carRepository.update(expectedCar);

        entityManager.flush();
        entityManager.clear();

        var actualCar = carRepository.findById(expectedCar.getId());

        Assertions.assertTrue(actualCar.isPresent());
        Assertions.assertEquals("UPDATE", actualCar.get().getModel());
    }

    @Test
    void deleteCar() {
        Client expectedClient = EntityUtil.buildRandomClient("Save", "Car");
        entityManager.persist(expectedClient);
        Car expectedCar = EntityUtil.buildCar("Ford", expectedClient);

        carRepository.save(expectedCar);

        carRepository.delete(expectedCar);

        entityManager.flush();
        entityManager.clear();

        Assertions.assertTrue(carRepository.findById(expectedCar.getId()).isEmpty());
    }
}