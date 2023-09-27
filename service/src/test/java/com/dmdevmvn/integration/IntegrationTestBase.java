package com.dmdevmvn.integration;

import com.dmdevmvn.config.ApplicationTestConfiguration;
import com.dmdevmvn.util.TestDataImporter;
import com.querydsl.jpa.support.QPostgreSQLDialect;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static com.dmdevmvn.util.ContainerTestUtil.postgres;

@SpringBootTest
@Transactional(readOnly = true)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class IntegrationTestBase {

    protected static final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ApplicationTestConfiguration.class);

    @Autowired
    private  SessionFactory sessionFactory;

    @Autowired
    protected EntityManager entityManager;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
    @BeforeAll
    void initDB() {
        TestDataImporter.importData(sessionFactory);
    }
//
//    @AfterAll
//    static void afterAll() {
//        context.close();
//    }

    @BeforeEach
    void setUp() {
//        entityManager.getTransaction().begin();
//        TestDataImporter.importData(sessionFactory);
    }

    @AfterEach
    void tearDown() {
//        entityManager.getTransaction().rollback();
    }
}
