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
class ClientIT {

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
    void saveClient() {
        Client expectedClient = EntityUtil.buildRandomClient("Ivan", "Ivanov");

        session.save(expectedClient);

        Client actualClient = session.get(Client.class, expectedClient.getId());

        assertNotNull(actualClient);
    }

    @Test
    void getClient() {
        Client expectedClient = EntityUtil.buildRandomClient("Ivan", "Ivanov");

        session.save(expectedClient);

        session.flush();
        session.clear();
        Client actualClient = session.get(Client.class, expectedClient.getId());

        assertThat(expectedClient).isEqualTo(actualClient);
    }

    @Test
    void updateClient() {
        Client expectedClient = EntityUtil.buildRandomClient("Ivan", "Ivanov");
        session.save(expectedClient);

        expectedClient.setFirstName("Viktor");
        session.flush();
        session.clear();

        Client actualClient = session.get(Client.class, expectedClient.getId());

        assertEquals("Viktor", actualClient.getFirstName());
    }

    @Test
    void deleteClient() {
        Client expectedClient = EntityUtil.buildRandomClient("Ivan", "Ivanov");
        session.save(expectedClient);

        session.delete(expectedClient);
        session.flush();
        session.clear();

        Client actualClient = session.get(Client.class, expectedClient.getId());

        assertNull(actualClient);
    }
}