package com.dmdevmvn.service.dao;

import com.dmdevmvn.service.entity.Client;
import com.dmdevmvn.service.util.EntityUtil;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ClientRepositoryTest {
    private static final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();

    private static final Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
            (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));

    private static final ClientRepository clientRepository = new ClientRepository(session);

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
        Client expectedClient = EntityUtil.buildRandomClient("Ivan", "Ivanov");
        clientRepository.save(expectedClient);

        session.clear();

        var actualClient = clientRepository.findById(expectedClient.getId());
        assertNotNull(actualClient);
    }

    @Test
    void delete() {
        Client expectedClient = EntityUtil.buildRandomClient("Ivan", "Ivanov");
        clientRepository.save(expectedClient);

        clientRepository.delete(expectedClient);

        session.flush();
        session.clear();

        assertTrue(clientRepository.findById(expectedClient.getId()).isEmpty());
    }

    @Test
    void update() {
        Client expectedClient = EntityUtil.buildRandomClient("Ivan", "Ivanov");
        clientRepository.save(expectedClient);

        expectedClient.setFirstName("Update");
        clientRepository.update(expectedClient);

        session.flush();
        session.clear();

        var optionalClient = clientRepository.findById(expectedClient.getId());

        assertTrue(optionalClient.isPresent());
        assertEquals("Update", optionalClient.get().getFirstName());
    }

    @Test
    void findById() {
        var optionalClient = clientRepository.findById(1L);

        assertNotNull(optionalClient);
    }

    @Test
    void findAll() {
        var clientRepositoryAll = clientRepository.findAll();

        assertThat(clientRepositoryAll).hasSize(3);
    }
}