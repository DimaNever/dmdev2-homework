package com.dmdevmvn.dao;

import com.dmdevmvn.entity.Car;
import com.dmdevmvn.entity.Client;
import com.dmdevmvn.util.EntityUtil;
import com.dmdevmvn.util.TestBase;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarRepositoryTest extends TestBase {

    private final CarRepository carRepository = context.getBean(CarRepository.class);

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

        assertNotNull(saveCar.getId());
    }

    @Test
    void readCar() {
        Client expectedClient = EntityUtil.buildRandomClient("Save", "Car");
        entityManager.persist(expectedClient);
        Car expectedCar = EntityUtil.buildCar("Ford", expectedClient);
        carRepository.save(expectedCar);

        entityManager.clear();

        Optional<Car> actualCar = carRepository.findById(expectedCar.getId());

        assertTrue(actualCar.isPresent());
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

        assertTrue(actualCar.isPresent());
        assertEquals("UPDATE", actualCar.get().getModel());
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

        assertTrue(carRepository.findById(expectedCar.getId()).isEmpty());
    }
}