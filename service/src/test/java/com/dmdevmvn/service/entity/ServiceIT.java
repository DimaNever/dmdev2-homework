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
import static com.dmdevmvn.service.util.EntityUtil.buildRandomUser;
import static com.dmdevmvn.service.util.EntityUtil.buildService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@Slf4j
class ServiceIT {
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
    void saveService() {
        User expectedUser = buildRandomUser("Save", "ServiceUser");
        Client expectedClient = buildRandomClient("Save", "ServiceClient");
        Car expectedCar = buildCar("BMW", expectedClient);
        Service expectedService = buildService(expectedUser, expectedCar);

        session.save(expectedUser);
        session.save(expectedClient);
        session.save(expectedCar);
        session.save(expectedService);
        session.clear();

        Service actualService = session.get(Service.class, expectedService.getId());

        assertNotNull(actualService.getId());
    }

    @Test
    void getService() {
        User expectedUser = buildRandomUser("Get", "ServiceUser");
        Client expectedClient = buildRandomClient("Get", "ServiceClient");
        Car expectedCar = buildCar("BMW", expectedClient);
        Service expectedService = buildService(expectedUser, expectedCar);

        session.save(expectedUser);
        session.save(expectedClient);
        session.save(expectedCar);
        session.save(expectedService);
        session.clear();

        Service actualService = session.get(Service.class, expectedService.getId());

        assertThat(expectedService).isEqualTo(actualService);
    }

    @Test
    void updateService() {
        User expectedUser = buildRandomUser("Update", "ServiceUser");
        Client expectedClient = buildRandomClient("Update", "ServiceClient");
        Car expectedCar = buildCar("BMW", expectedClient);
        Service expectedService = buildService(expectedUser, expectedCar);

        session.save(expectedUser);
        session.save(expectedClient);
        session.save(expectedCar);
        session.save(expectedService);

        expectedService.setPrice(1_000_000L);
        session.flush();
        session.clear();

        Service actualService = session.get(Service.class, expectedService.getId());

        assertEquals(1_000_000L, actualService.getPrice());
    }

    @Test
    void deleteService() {
        User expectedUser = buildRandomUser("Delete", "ServiceUser");
        Client expectedClient = buildRandomClient("Delete", "ServiceClient");
        Car expectedCar = buildCar("BMW", expectedClient);
        Service expectedService = buildService(expectedUser, expectedCar);

        session.save(expectedUser);
        session.save(expectedClient);
        session.save(expectedCar);
        session.save(expectedService);

        session.delete(expectedService);
        session.flush();
        session.clear();

        Service actualService = session.get(Service.class, expectedService.getId());

        assertNull(actualService);
    }

    @AfterAll
    static void afterTests() {
        sessionFactory.close();
    }
}