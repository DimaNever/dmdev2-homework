package com.dmdevmvn.util;

import com.dmdevmvn.config.ApplicationTestConfiguration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class TestBase {

    protected static final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationTestConfiguration.class);
    private static final SessionFactory sessionFactory = context.getBean(SessionFactory.class);
    protected static final Session session = context.getBean(Session.class);

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
        session.beginTransaction();
    }

    @AfterEach
    void tearDown() {
        session.getTransaction().rollback();
    }
}
