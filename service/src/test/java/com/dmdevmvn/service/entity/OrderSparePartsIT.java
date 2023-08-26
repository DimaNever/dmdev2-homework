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
import static com.dmdevmvn.service.util.EntityUtil.buildRandomSparePart;
import static com.dmdevmvn.service.util.EntityUtil.buildRandomUser;
import static com.dmdevmvn.service.util.EntityUtil.buildOrder;
import static com.dmdevmvn.service.util.EntityUtil.buildOrderSpareParts;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
class OrderSparePartsIT {
    private static SessionFactory sessionFactory;
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
    void saveOrderSpareParts() {
        User user = buildRandomUser("User", "OrderSpareParts");
        Client client = buildRandomClient("Client", "OrderSpareParts");
        Car car = buildCar("Mercedes", client);
        Order order = buildOrder(user, car);
        SparePart sparePart = buildRandomSparePart();
        OrderSpareParts expectedOrderSpareParts = buildOrderSpareParts(order, sparePart);

        session.save(user);
        session.save(client);
        session.save(car);
        session.save(order);
        session.save(sparePart);
        session.save(expectedOrderSpareParts);
        session.clear();

        OrderSpareParts actualOrderSpareParts = session.get(OrderSpareParts.class, expectedOrderSpareParts.getId());

        assertNotNull(actualOrderSpareParts);
    }

    @Test
    void getOrderSpareParts() {
        User user = buildRandomUser("User", "OrderSpareParts");
        Client client = buildRandomClient("Client", "OrderSpareParts");
        Car car = buildCar("Mercedes", client);
        Order order = buildOrder(user, car);
        SparePart sparePart = buildRandomSparePart();
        OrderSpareParts expectedOrderSpareParts = buildOrderSpareParts(order, sparePart);

        session.save(user);
        session.save(client);
        session.save(car);
        session.save(order);
        session.save(sparePart);
        session.save(expectedOrderSpareParts);
        session.clear();

        OrderSpareParts actualOrderSpareParts = session.get(OrderSpareParts.class, expectedOrderSpareParts.getId());

        assertThat(expectedOrderSpareParts).isEqualTo(actualOrderSpareParts);
    }

    @Test
    void updateOrderSpareParts() {
        User user = buildRandomUser("User", "OrderSpareParts");
        Client client = buildRandomClient("Client", "OrderSpareParts");
        Car car = buildCar("Mercedes", client);
        Order order = buildOrder(user, car);
        SparePart sparePart = buildRandomSparePart();
        OrderSpareParts expectedOrderSpareParts = buildOrderSpareParts(order, sparePart);

        session.save(user);
        session.save(client);
        session.save(car);
        session.save(order);
        session.save(sparePart);
        session.save(expectedOrderSpareParts);

        expectedOrderSpareParts.setQuantity(999);
        session.flush();
        session.clear();

        OrderSpareParts actualOrderSpareParts = session.get(OrderSpareParts.class, expectedOrderSpareParts.getId());

        assertEquals(999, actualOrderSpareParts.getQuantity());
    }

    @Test
    void deleteOrderSpareParts() {
        User user = buildRandomUser("User", "OrderSpareParts");
        Client client = buildRandomClient("Client", "OrderSpareParts");
        Car car = buildCar("Mercedes", client);
        Order order = buildOrder(user, car);
        SparePart sparePart = buildRandomSparePart();
        OrderSpareParts expectedOrderSpareParts = buildOrderSpareParts(order, sparePart);

        session.save(user);
        session.save(client);
        session.save(car);
        session.save(order);
        session.save(sparePart);
        session.save(expectedOrderSpareParts);

        session.delete(expectedOrderSpareParts);
        session.flush();
        session.clear();

        OrderSpareParts actualOrderSpareParts = session.get(OrderSpareParts.class, expectedOrderSpareParts.getId());

        assertNull(actualOrderSpareParts);
    }
}