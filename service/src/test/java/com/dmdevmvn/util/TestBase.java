package com.dmdevmvn.util;

import com.dmdevmvn.config.ApplicationTestConfiguration;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.persistence.EntityManager;

public class TestBase {

    protected static final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationTestConfiguration.class);
    private static final SessionFactory sessionFactory = context.getBean(SessionFactory.class);
    protected static final EntityManager entityManager = context.getBean(EntityManager.class);

    @BeforeAll
    static void initDB() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    static void afterAll() {
        context.close();
    }

    @BeforeEach
    void setUp() {
        entityManager.getTransaction().begin();
    }

    @AfterEach
    void tearDown() {
        entityManager.getTransaction().rollback();
    }
}
