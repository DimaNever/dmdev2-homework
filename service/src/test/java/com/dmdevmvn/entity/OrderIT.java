package com.dmdevmvn.entity;

import com.dmdevmvn.util.HibernateTestUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.dmdevmvn.util.EntityUtil.buildCar;
import static com.dmdevmvn.util.EntityUtil.buildRandomClient;
import static com.dmdevmvn.util.EntityUtil.buildRandomUser;
import static com.dmdevmvn.util.EntityUtil.buildOrder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
class OrderIT {

    private static  SessionFactory sessionFactory;
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
    void saveOrder() {
        User expectedUser = buildRandomUser("Save", "ServiceUser", Role.ADMIN);
        Client expectedClient = buildRandomClient("Save", "ServiceClient");
        Car expectedCar = buildCar("BMW", expectedClient);
        Order expectedOrder = buildOrder(expectedUser, expectedCar, ServiceType.MAINTENANCE);

        session.save(expectedUser);
        session.save(expectedClient);
        session.save(expectedCar);
        session.save(expectedOrder);
        session.clear();

        Order actualOrder = session.get(Order.class, expectedOrder.getId());

        assertNotNull(actualOrder);
    }

    @Test
    void getOrder() {
        User expectedUser = buildRandomUser("Get", "ServiceUser", Role.ADMIN);
        Client expectedClient = buildRandomClient("Get", "ServiceClient");
        Car expectedCar = buildCar("BMW", expectedClient);
        Order expectedOrder = buildOrder(expectedUser, expectedCar, ServiceType.MAINTENANCE);

        session.save(expectedUser);
        session.save(expectedClient);
        session.save(expectedCar);
        session.save(expectedOrder);
        session.clear();

        Order actualOrder = session.get(Order.class, expectedOrder.getId());

        assertThat(expectedOrder).isEqualTo(actualOrder);
    }

    @Test
    void updateOrder() {
        User expectedUser = buildRandomUser("Update", "ServiceUser", Role.ADMIN);
        Client expectedClient = buildRandomClient("Update", "ServiceClient");
        Car expectedCar = buildCar("BMW", expectedClient);
        Order expectedOrder = buildOrder(expectedUser, expectedCar, ServiceType.MAINTENANCE);

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
    void deleteOrder() {
        User expectedUser = buildRandomUser("Delete", "ServiceUser", Role.ADMIN);
        Client expectedClient = buildRandomClient("Delete", "ServiceClient");
        Car expectedCar = buildCar("BMW", expectedClient);
        Order expectedOrder = buildOrder(expectedUser, expectedCar, ServiceType.MAINTENANCE);

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