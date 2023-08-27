package com.dmdevmvn.service.entity;

import com.dmdevmvn.service.util.EntityUtil;
import com.dmdevmvn.util.HibernateTestUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
class CarIT {
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
    void saveCar() {
        Client expectedClient = EntityUtil.buildRandomClient("Save", "Car");
        Car expectedCar = EntityUtil.buildCar("Ford", expectedClient);

        session.save(expectedClient);
        session.save(expectedCar);
        session.clear();

        Car actualCar = session.get(Car.class, expectedCar.getId());

        assertNotNull(actualCar);
    }

    @Test
    void getCar() {
        Client client = EntityUtil.buildRandomClient("Get", "Car");
        Car expectedCar = EntityUtil.buildCar("Audi", client);
        session.save(client);
        session.save(expectedCar);
        session.clear();

        Car actualCar = session.get(Car.class, expectedCar.getId());

        assertThat(expectedCar).isEqualTo(actualCar);
    }

    @Test
    void updateCar() {
        Client client = EntityUtil.buildRandomClient("Client", "Car");
        Car expectedCar = EntityUtil.buildCar("Audi", client);
        session.save(client);
        session.save(expectedCar);

        expectedCar.setModel("Update");
        session.flush();
        session.clear();

        Car actualCar = session.get(Car.class, expectedCar.getId());

        assertEquals("Update", actualCar.getModel());
    }

    @Test
    void deleteCar() {
        Client client = EntityUtil.buildRandomClient("Client", "Car");
        Car expectedCar = EntityUtil.buildCar("Audi", client);
        session.save(client);
        session.save(expectedCar);

        session.delete(expectedCar);
        session.flush();
        session.clear();

        Car actualCar = session.get(Car.class, expectedCar.getId());

        assertNull(actualCar);
    }
}