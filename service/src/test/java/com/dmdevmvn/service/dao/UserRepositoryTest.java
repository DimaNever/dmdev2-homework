package com.dmdevmvn.service.dao;

import com.dmdevmvn.service.entity.Role;
import com.dmdevmvn.service.entity.User;
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

class UserRepositoryTest {
    private static final SessionFactory sessionFactory = HibernateTestUtil.buildSessionFactory();

    private static final Session session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
            (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));

    private static final UserRepository userRepository = new UserRepository(session);

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
        User expectedUser = EntityUtil.buildRandomUser("Save", "John", Role.ADMIN);
        userRepository.save(expectedUser);

        session.clear();

        var optionalUser = userRepository.findById(expectedUser.getId());
        assertNotNull(optionalUser);
    }

    @Test
    void delete() {
        User expectedUser = EntityUtil.buildRandomUser("Save", "John", Role.ADMIN);
        userRepository.save(expectedUser);

        userRepository.delete(expectedUser);

        session.flush();
        session.clear();

        assertTrue(userRepository.findById(expectedUser.getId()).isEmpty());
    }

    @Test
    void update() {
        User expectedUser = EntityUtil.buildRandomUser("Save", "John", Role.ADMIN);
        userRepository.save(expectedUser);

        expectedUser.setFirstName("Update");
        userRepository.update(expectedUser);

        session.flush();
        session.clear();

        var optionalUser = userRepository.findById(expectedUser.getId());

        assertTrue(optionalUser.isPresent());
        assertEquals("Update", optionalUser.get().getFirstName());
    }

    @Test
    void findById() {
        var optionalUser = userRepository.findById(1L);

        assertNotNull(optionalUser);
    }

    @Test
    void findAll() {
        var userRepositoryAll = userRepository.findAll();

        assertThat(userRepositoryAll).hasSize(3);
    }

    @Test
    void findAllByRole() {
        var userRepositoryAllByRole = userRepository.findAllByRole(Role.WORKER);

        assertThat(userRepositoryAllByRole).hasSize(2);
    }
}