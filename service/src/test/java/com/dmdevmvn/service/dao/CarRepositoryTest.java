package com.dmdevmvn.service.dao;

import com.dmdevmvn.service.entity.Car;
import com.dmdevmvn.service.entity.Client;
import com.dmdevmvn.service.util.EntityUtil;
import com.dmdevmvn.util.HibernateTestUtil;
import com.dmdevmvn.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CarRepositoryTest {
    private static final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();

    private static final Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
            (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));

    private static final CarRepository carRepository = new CarRepository(session);

    @BeforeAll
    static void initDB() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    static void afterAll() {
        sessionFactory.close();
    }

    @BeforeEach
    void setUp() {
        session.beginTransaction();
    }

    @AfterEach
    void tearDown() {
        session.getTransaction().rollback();
    }

    @Test
    void findAllCarsByClientId() {
        List<Car> carList = carRepository.findAllCarsByClientId(1L);
        assertThat(carList).hasSize(2);

        var models = carList.stream().map(Car::getModel).collect(toSet());
        assertThat(models).containsExactlyInAnyOrder("Audi", "Lada");
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

        session.clear();

        assertNotNull(saveCar.getId());
    }

    @Test
    void readCar() {
        Client expectedClient = EntityUtil.buildRandomClient("Save", "Car");
        session.save(expectedClient);
        Car expectedCar = EntityUtil.buildCar("Ford", expectedClient);
        carRepository.save(expectedCar);

        session.clear();

        Optional<Car> actualCar = carRepository.findById(expectedCar.getId());

        assertTrue(actualCar.isPresent());
        assertThat(actualCar.get()).isEqualTo(expectedCar);
    }

    @Test
    void updateCar() {
        Client expectedClient = EntityUtil.buildRandomClient("Save", "Car");
        session.save(expectedClient);
        Car expectedCar = EntityUtil.buildCar("Ford", expectedClient);

        carRepository.save(expectedCar);

        expectedCar.setModel("UPDATE");
        carRepository.update(expectedCar);

        session.flush();
        session.clear();

        var actualCar = carRepository.findById(expectedCar.getId());

        assertTrue(actualCar.isPresent());
        assertEquals("UPDATE", actualCar.get().getModel());
    }

    @Test
    void deleteCar() {
        Client expectedClient = EntityUtil.buildRandomClient("Save", "Car");
        session.save(expectedClient);
        Car expectedCar = EntityUtil.buildCar("Ford", expectedClient);

        carRepository.save(expectedCar);

        carRepository.delete(expectedCar);

        session.flush();
        session.clear();

        assertTrue(carRepository.findById(expectedCar.getId()).isEmpty());
    }
}