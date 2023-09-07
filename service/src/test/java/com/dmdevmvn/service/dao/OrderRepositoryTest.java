package com.dmdevmvn.service.dao;

import com.dmdevmvn.service.entity.Car;
import com.dmdevmvn.service.entity.Client;
import com.dmdevmvn.service.entity.Order;
import com.dmdevmvn.service.entity.Role;
import com.dmdevmvn.service.entity.ServiceType;
import com.dmdevmvn.service.entity.User;
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

import static com.dmdevmvn.service.util.EntityUtil.buildCar;
import static com.dmdevmvn.service.util.EntityUtil.buildOrder;
import static com.dmdevmvn.service.util.EntityUtil.buildRandomClient;
import static com.dmdevmvn.service.util.EntityUtil.buildRandomUser;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderRepositoryTest {
    private static final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();

    private static final Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
            (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));

    private static final OrderRepository orderRepository = new OrderRepository(session);

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
    void save() {
        User expectedUser = buildRandomUser("Save", "User", Role.ADMIN);
        Client expectedClient = buildRandomClient("Save", "Client");
        Car expectedCar = buildCar("BMW", expectedClient);
        Order expectedOrder = buildOrder(expectedUser, expectedCar, ServiceType.MAINTENANCE);

        session.save(expectedUser);
        session.save(expectedClient);
        session.save(expectedCar);
        orderRepository.save(expectedOrder);
        session.clear();

        var optionalOrder = orderRepository.findById(expectedOrder.getId());
        assertNotNull(optionalOrder);
    }

    @Test
    void delete() {
        User expectedUser = buildRandomUser("Save", "User", Role.ADMIN);
        Client expectedClient = buildRandomClient("Save", "Client");
        Car expectedCar = buildCar("BMW", expectedClient);
        Order expectedOrder = buildOrder(expectedUser, expectedCar, ServiceType.MAINTENANCE);

        session.save(expectedUser);
        session.save(expectedClient);
        session.save(expectedCar);
        orderRepository.save(expectedOrder);

        orderRepository.delete(expectedOrder);

        session.flush();
        session.clear();

        assertTrue(orderRepository.findById(expectedOrder.getId()).isEmpty());
    }

    @Test
    void update() {
        User expectedUser = buildRandomUser("Save", "User", Role.ADMIN);
        Client expectedClient = buildRandomClient("Save", "Client");
        Car expectedCar = buildCar("BMW", expectedClient);
        Order expectedOrder = buildOrder(expectedUser, expectedCar, ServiceType.MAINTENANCE);

        session.save(expectedUser);
        session.save(expectedClient);
        session.save(expectedCar);
        orderRepository.save(expectedOrder);

        expectedOrder.setServiceType(ServiceType.REPAIR);
        orderRepository.update(expectedOrder);

        session.flush();
        session.clear();

        var optionalOrder = orderRepository.findById(expectedOrder.getId());

        assertTrue(optionalOrder.isPresent());
        assertEquals(ServiceType.REPAIR, optionalOrder.get().getServiceType());
    }

    @Test
    void findById() {
        var optionalOrder = orderRepository.findById(1L);

        assertNotNull(optionalOrder);
    }

    @Test
    void findAll() {
        var orderRepositoryAll = orderRepository.findAll();

        assertThat(orderRepositoryAll).hasSize(7);
    }

    @Test
    void findAllOrdersByCarId() {
        var allOrdersByCarId = orderRepository.findAllOrdersByCarId(1L);

        assertThat(allOrdersByCarId).hasSize(3);
    }

    @Test
    void findAllOrdersByClientId() {
        var allOrdersByClientId = orderRepository.findAllOrdersByClientId(1L);
        var modelCars = allOrdersByClientId.stream()
                .map(Order::getCar)
                .map(Car::getModel)
                .collect(toSet());

        assertThat(allOrdersByClientId).hasSize(4);
        assertThat(modelCars).containsExactlyInAnyOrder("Lada", "Audi");
    }
}