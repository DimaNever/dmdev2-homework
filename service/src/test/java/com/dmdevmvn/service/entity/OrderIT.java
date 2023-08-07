package com.dmdevmvn.service.entity;

import com.dmdevmvn.util.HibernateTestUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.dmdevmvn.service.util.EntityUtil.buildCar;
import static com.dmdevmvn.service.util.EntityUtil.buildRandomClient;
import static com.dmdevmvn.service.util.EntityUtil.buildRandomUser;
import static com.dmdevmvn.service.util.EntityUtil.buildOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
class OrderIT {
    private static  SessionFactory sessionFactory;
    private Session session;

    @BeforeAll
    static void beforeTests() {
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
    static void afterTests() {
        sessionFactory.close();
    }

    @Test
    void saveService() {
        User expectedUser = buildRandomUser("Save", "ServiceUser");
        Client expectedClient = buildRandomClient("Save", "ServiceClient");
        Car expectedCar = buildCar("BMW", expectedClient);
        Order expectedOrder = buildOrder(expectedUser, expectedCar);

        session.save(expectedUser);
        session.save(expectedClient);
        session.save(expectedCar);
        session.save(expectedOrder);
        session.clear();

        Order actualOrder = session.get(Order.class, expectedOrder.getId());

        assertNotNull(actualOrder);
    }

    @Test
    void getService() {
        User expectedUser = buildRandomUser("Get", "ServiceUser");
        Client expectedClient = buildRandomClient("Get", "ServiceClient");
        Car expectedCar = buildCar("BMW", expectedClient);
        Order expectedOrder = buildOrder(expectedUser, expectedCar);

        session.save(expectedUser);
        session.save(expectedClient);
        session.save(expectedCar);
        session.save(expectedOrder);
        session.clear();

        Order actualOrder = session.get(Order.class, expectedOrder.getId());

        assertThat(expectedOrder).isEqualTo(actualOrder);
    }

    @Test
    void updateService() {
        User expectedUser = buildRandomUser("Update", "ServiceUser");
        Client expectedClient = buildRandomClient("Update", "ServiceClient");
        Car expectedCar = buildCar("BMW", expectedClient);
        Order expectedOrder = buildOrder(expectedUser, expectedCar);

        session.save(expectedUser);
        session.save(expectedClient);
        session.save(expectedCar);
        session.save(expectedOrder);

        expectedOrder.setPrice(1_000_000L);
        session.flush();
        session.clear();

        Order actualOrder = session.get(Order.class, expectedOrder.getId());

        assertEquals(1_000_000L, actualOrder.getPrice());
    }

    @Test
    void deleteService() {
        User expectedUser = buildRandomUser("Delete", "ServiceUser");
        Client expectedClient = buildRandomClient("Delete", "ServiceClient");
        Car expectedCar = buildCar("BMW", expectedClient);
        Order expectedOrder = buildOrder(expectedUser, expectedCar);

        session.save(expectedUser);
        session.save(expectedClient);
        session.save(expectedCar);
        session.save(expectedOrder);

        session.delete(expectedOrder);
        session.flush();
        session.clear();

        Order actualOrder = session.get(Order.class, expectedOrder.getId());

        assertNull(actualOrder);
    }
}