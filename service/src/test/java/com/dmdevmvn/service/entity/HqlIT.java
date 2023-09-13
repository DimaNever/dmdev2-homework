package com.dmdevmvn.service.entity;

import com.dmdevmvn.service.util.EntityUtil;
import com.dmdevmvn.util.HibernateTestUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.QueryHints;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HqlIT {


    private static SessionFactory sessionFactory;
    private Session session;

    @BeforeAll
    static void beforeAll() {
        sessionFactory = HibernateTestUtil.buildSessionFactory();
    }

    @BeforeEach
    void setUp() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterEach
    void tearDown() {
        session.getTransaction().rollback();
        session.close();
    }

    @AfterAll
    static void afterAll() {
        sessionFactory.close();
    }

    @Test
    void checkHql() {
        Client client = EntityUtil.buildRandomClient("Save", "Car");
        Car car = EntityUtil.buildCar("Ford", client);

        session.save(client);
        session.save(car);
        session.clear();

        String model = "Ford";
        var result = session.createQuery(
                        "select car from Car car " +
                        "join car.client cl " +
                        "where car.model = :title and cl.firstName = :name", Car.class)
                .setParameter("title", model)
                .setParameter("name", "Save")
                .uniqueResultOptional();
    }

    @Test
    void checkNamedQueryHql() {
        Client client = EntityUtil.buildRandomClient("Save", "Car");
        Car car = EntityUtil.buildCar("Ford", client);

        session.save(client);
        session.save(car);
        session.clear();

        String model = "Ford";
        var result = session.createNamedQuery(
                        "findCarByModel", Car.class)
                .setParameter("title", model)
                .setParameter("name", "Save")
                .setHint(QueryHints.FETCH_SIZE, "50")
                .uniqueResult();
        session.createQuery("update Car c set c.year = 5000" +
                            "where c.model = 'Ford'")
                .executeUpdate();
    }
}
