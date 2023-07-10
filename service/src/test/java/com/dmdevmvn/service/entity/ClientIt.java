package com.dmdevmvn.service.entity;

import com.dmdevmvn.service.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ClientIt {

    @Test
    void saveClient() {

        Client expectedClient = buildRandomClient(81112223344L, "test@gmail.com");

        try (
                SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
                Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(expectedClient);
            session.flush();
            session.clear();

            Client actualClient = session.get(Client.class, expectedClient.getId());
            System.out.println(actualClient);

            assertThat(expectedClient).isEqualTo(actualClient);

            session.getTransaction().rollback();
        }
    }

    @Test
    void getUser() {
        Client expectedClient = buildRandomClient(87771112233L, "test@gmail.com");

        try (
                SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
                Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(expectedClient);

            session.getTransaction().commit();
            session.clear();
            Client actualClient = session.get(Client.class, expectedClient.getId());
            System.out.println(actualClient);

            assertThat(expectedClient).isEqualTo(actualClient);
        }
    }

    private static Client buildRandomClient(Long phoneNumber, String email) {
        Long random = new Random().nextLong(10000);
        Client client = Client.builder()
                .firstName("Petr")
                .lastName("Petrov")
                .phoneNumber(phoneNumber + random)
                .email(random + email)
                .build();
        return client;
    }
}