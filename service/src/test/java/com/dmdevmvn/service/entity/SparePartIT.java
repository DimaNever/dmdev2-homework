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
class SparePartIT {
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
    void saveSparePart() {
        SparePart expectedSparePart = EntityUtil.buildRandomSparePart();

        session.save(expectedSparePart);
        session.clear();

        SparePart actualSparePart = session.get(SparePart.class, expectedSparePart.getId());

        assertNotNull(actualSparePart);
    }

    @Test
    void getSparePart() {
        SparePart expectedSparePart = EntityUtil.buildRandomSparePart();

        session.save(expectedSparePart);
        session.clear();

        SparePart actualSparePart = session.get(SparePart.class, expectedSparePart.getId());

        assertThat(expectedSparePart).isEqualTo(actualSparePart);
    }

    @Test
    void updateSparePart() {
        SparePart expectedSparePart = EntityUtil.buildRandomSparePart();
        session.save(expectedSparePart);

        expectedSparePart.setTitle("Update");
        session.flush();
        session.clear();

        SparePart actualSparePart = session.get(SparePart.class, expectedSparePart.getId());

        assertEquals("Update", actualSparePart.getTitle());
    }

    @Test
    void deleteSparePart() {
        SparePart expectedSparePart = EntityUtil.buildRandomSparePart();
        session.save(expectedSparePart);

        session.delete(expectedSparePart);
        session.flush();
        session.clear();

        SparePart actualSparePart = session.get(SparePart.class, expectedSparePart.getId());

        assertNull(actualSparePart);
    }
}