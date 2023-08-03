package com.dmdevmvn.service.entity;

import com.dmdevmvn.service.util.HibernateUtil;
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
import static com.dmdevmvn.service.util.EntityUtil.buildService;
import static com.dmdevmvn.service.util.EntityUtil.buildServiceSpareParts;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
class ServiceSparePartsIT {
    static private SessionFactory sessionFactory;
    private Session session;

    @BeforeAll
    static void beforeTests() {
        sessionFactory = HibernateUtil.buildSessionFactory();
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

    @Test
    void saveServiceSpareParts() {
        User user = buildRandomUser("User", "ServiceSpareParts");
        Client client = buildRandomClient("Client", "ServiceSpareParts");
        Car car = buildCar("Mercedes", client);
        Service service = buildService(user, car);
        SparePart sparePart = buildRandomSparePart();
        ServiceSpareParts expectedServiceSpareParts = buildServiceSpareParts(service, sparePart);

        session.save(user);
        session.save(client);
        session.save(car);
        session.save(service);
        session.save(sparePart);
        session.save(expectedServiceSpareParts);
        session.clear();

        ServiceSpareParts actualServiceSpareParts = session.get(ServiceSpareParts.class, expectedServiceSpareParts.getId());

        assertNotNull(actualServiceSpareParts.getId());
    }

    @Test
    void getServiceSpareParts() {
        User user = buildRandomUser("User", "ServiceSpareParts");
        Client client = buildRandomClient("Client", "ServiceSpareParts");
        Car car = buildCar("Mercedes", client);
        Service service = buildService(user, car);
        SparePart sparePart = buildRandomSparePart();
        ServiceSpareParts expectedServiceSpareParts = buildServiceSpareParts(service, sparePart);

        session.save(user);
        session.save(client);
        session.save(car);
        session.save(service);
        session.save(sparePart);
        session.save(expectedServiceSpareParts);
        session.clear();

        ServiceSpareParts actualServiceSpareParts = session.get(ServiceSpareParts.class, expectedServiceSpareParts.getId());

        assertThat(expectedServiceSpareParts).isEqualTo(actualServiceSpareParts);
    }

    @Test
    void updateServiceSpareParts() {
        User user = buildRandomUser("User", "ServiceSpareParts");
        Client client = buildRandomClient("Client", "ServiceSpareParts");
        Car car = buildCar("Mercedes", client);
        Service service = buildService(user, car);
        SparePart sparePart = buildRandomSparePart();
        ServiceSpareParts expectedServiceSpareParts = buildServiceSpareParts(service, sparePart);

        session.save(user);
        session.save(client);
        session.save(car);
        session.save(service);
        session.save(sparePart);
        session.save(expectedServiceSpareParts);

        expectedServiceSpareParts.setQuantity(999);
        session.flush();
        session.clear();

        ServiceSpareParts actualServiceSpareParts = session.get(ServiceSpareParts.class, expectedServiceSpareParts.getId());

        assertEquals(999, actualServiceSpareParts.getQuantity());
    }

    @Test
    void deleteServiceSpareParts() {
        User user = buildRandomUser("User", "ServiceSpareParts");
        Client client = buildRandomClient("Client", "ServiceSpareParts");
        Car car = buildCar("Mercedes", client);
        Service service = buildService(user, car);
        SparePart sparePart = buildRandomSparePart();
        ServiceSpareParts expectedServiceSpareParts = buildServiceSpareParts(service, sparePart);

        session.save(user);
        session.save(client);
        session.save(car);
        session.save(service);
        session.save(sparePart);
        session.save(expectedServiceSpareParts);

        session.delete(expectedServiceSpareParts);
        session.flush();
        session.clear();

        ServiceSpareParts actualServiceSpareParts = session.get(ServiceSpareParts.class, expectedServiceSpareParts.getId());

        assertNull(actualServiceSpareParts);
    }

    @AfterAll
    static void afterTests() {
        sessionFactory.close();
    }
}