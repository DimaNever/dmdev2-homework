package com.dmdevmvn.service.dao;

import com.dmdevmvn.service.entity.Car;
import com.dmdevmvn.util.HibernateTestUtil;
import com.dmdevmvn.util.TestDataImporter;
import lombok.Cleanup;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class CarDaoWithHqlHqlIT {

    private static final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
    private final CarDaoWithHql carDaoWithHql = CarDaoWithHql.getInstance();

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

        List<Car> carList = carDaoWithHql.findAll(session);
        assertThat(carList).hasSize(5);

        var models = carList.stream().map(Car::getModel).collect(toSet());
        assertThat(models).containsExactlyInAnyOrder("Audi", "Lada", "Ford", "Nissan");
        session.getTransaction().rollback();
    }

    @Test
    void findAllByModel() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Car> carList = carDaoWithHql.findAllByModel(session, "Audi");

        assertThat(carList).hasSize(2);
        assertThat(carList.get(0).getModel()).isEqualTo("Audi");

        session.getTransaction().rollback();
    }

    @Test
    void findLimitedCarOrderByYear() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        int limit = 3;
        List<Car> carList = carDaoWithHql.findLimitedCarOrderByYear(session, limit);
        assertThat(carList).hasSize(limit);

        var models = carList.stream().map(Car::getModel).collect(toSet());
        assertThat(models).containsAnyOf("Audi", "Lada", "Ford", "Nissan");

        session.getTransaction().rollback();
    }

    @Test
    void findAllCarsByClientLastName() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Car> carList = carDaoWithHql.findAllCarsByClientLastName(session, "Petrov");
        assertThat(carList).hasSize(2);

        var models = carList.stream().map(Car::getModel).collect(toList());
        assertThat(models).containsExactlyInAnyOrder("Audi", "Ford");

        session.getTransaction().rollback();
    }

    @Test
    void findClientLastNameWithAvgCarMileageOrderByClientLastName() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Object[]> result = carDaoWithHql.findClientLastNameWithAvgCarMileageOrderByClientLastName(session);
        assertThat(result).hasSize(3);
        var lastNames = result.stream().map(a -> (String) a[0]).toList();

        assertThat(lastNames).containsExactly("ATestov", "Ivanov", "Petrov");

        session.getTransaction().rollback();
    }
}
