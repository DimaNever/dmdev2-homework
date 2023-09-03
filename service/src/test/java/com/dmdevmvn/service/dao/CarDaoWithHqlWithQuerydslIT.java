package com.dmdevmvn.service.dao;

import com.dmdevmvn.service.dto.CarFilter;
import com.dmdevmvn.service.entity.Car;
import com.dmdevmvn.util.HibernateTestUtil;
import com.dmdevmvn.util.TestDataImporter;
import com.querydsl.core.Tuple;
import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

public class CarDaoWithHqlWithQuerydslIT {

    private static final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
    private final CarDaoWithQuerydsl carDaoWithQuerydsl = CarDaoWithQuerydsl.getInstance();

    @BeforeAll
    static void initDB() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    static void afterAll() {
        sessionFactory.close();
    }

    @Test
    void findAll() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Car> carList = carDaoWithQuerydsl.findAll(session);
        assertThat(carList).hasSize(5);

        var models = carList.stream().map(Car::getModel).collect(toSet());
        assertThat(models).containsExactlyInAnyOrder("Audi", "Lada", "Ford", "Nissan");
        session.getTransaction().rollback();
    }

    @Test
    void findAllByModel() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Car> carList = carDaoWithQuerydsl.findAllByModel(session, "Audi");

        assertThat(carList).hasSize(2);
        assertThat(carList.get(0).getModel()).isEqualTo("Audi");

        session.getTransaction().rollback();
    }

    @Test
    void findLimitedCarOrderByYear() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        int limit = 3;
        List<Car> carList = carDaoWithQuerydsl.findLimitedCarOrderByYear(session, limit);
        assertThat(carList).hasSize(limit);

        var models = carList.stream().map(Car::getModel).collect(toSet());
        assertThat(models).containsAnyOf("Audi", "Lada", "Ford", "Nissan");

        session.getTransaction().rollback();
    }

    @Test
    void findAllCarsByClientLastName() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Car> carList = carDaoWithQuerydsl.findAllCarsByClientLastName(session, "Petrov");
        assertThat(carList).hasSize(2);

        var models = carList.stream().map(Car::getModel).collect(toList());
        assertThat(models).containsExactlyInAnyOrder("Audi", "Ford");

        session.getTransaction().rollback();
    }

    @Test
    void findClientLastNameWithAvgCarMileageOrderByClientLastName() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Tuple> result = carDaoWithQuerydsl.findClientLastNameWithAvgCarMileageOrderByClientLastName(session);
        assertThat(result).hasSize(3);
        var lastNames = result.stream().map(it -> it.get(0, String.class)).toList();

        assertThat(lastNames).containsExactly("ATestov", "Ivanov", "Petrov");

        session.getTransaction().rollback();
    }

    @Test
    void findAllCarsByClientLastNameWithFilter() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        CarFilter filter = CarFilter.builder()
                .lastName("Petrov")
                .firstName("Petr")
                .build();

        List<Car> carList = carDaoWithQuerydsl.findAllCarsByClientLastNameWithFilter(session, filter);
        assertThat(carList).hasSize(2);

        var models = carList.stream().map(Car::getModel).collect(toList());
        assertThat(models).containsExactlyInAnyOrder("Audi", "Ford");

        session.getTransaction().rollback();
    }
}
