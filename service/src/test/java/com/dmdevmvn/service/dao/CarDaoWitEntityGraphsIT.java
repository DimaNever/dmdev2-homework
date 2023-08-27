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
import static org.assertj.core.api.Assertions.assertThat;

public class CarDaoWitEntityGraphsIT {

    private static final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();
    private final CarDaoWithEntityGraphs carDaoWithEntityGraphs = CarDaoWithEntityGraphs.getInstance();

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

        List<Car> carList = carDaoWithEntityGraphs.findAll(session);
        assertThat(carList).hasSize(5);

        var models = carList.stream().map(Car::getModel).collect(toSet());
        assertThat(models).containsExactlyInAnyOrder("Audi", "Lada", "Ford", "Nissan");
        session.getTransaction().rollback();
    }

    @Test
    void findAllById() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Car car = carDaoWithEntityGraphs.findAllById(session, 1L);

        assertThat(car.getModel()).isEqualTo("Audi");

        session.getTransaction().rollback();
    }
}
