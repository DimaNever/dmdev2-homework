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
class UserIT {
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
    void saveUser() {
        User expectedUser = EntityUtil.buildRandomUser("Save", "John");

        session.save(expectedUser);

        User actualUser = session.get(User.class, expectedUser.getId());

        assertNotNull(actualUser);
    }

    @Test
    void getUser() {
        User expectedUser = EntityUtil.buildRandomUser("Get", "Sidorov");

        session.save(expectedUser);
        session.clear();

        User actualUser = session.get(User.class, expectedUser.getId());

        assertThat(expectedUser).isEqualTo(actualUser);
    }

    @Test
    void updateUser() {
        User expectedUser = EntityUtil.buildRandomUser("Update", "Testov");
        session.save(expectedUser);

        expectedUser.setFirstName("New");
        session.flush();
        session.clear();

        User actualUser = session.get(User.class, expectedUser.getId());

        assertEquals("New", actualUser.getFirstName());
    }

    @Test
    void deleteUser() {
        User expectedUser = EntityUtil.buildRandomUser("Delete", "Testov");
        session.save(expectedUser);

        session.delete(expectedUser);
        session.flush();
        session.clear();

        User actualUser = session.get(User.class, expectedUser.getId());

        assertNull(actualUser);
    }
}