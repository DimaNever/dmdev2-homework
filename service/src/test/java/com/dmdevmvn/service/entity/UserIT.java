package com.dmdevmvn.service.entity;

import com.dmdevmvn.service.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
class UserIT {

    @Test
    void saveUser() {

        User expectedUser = buildUser(87771112233L, "test1@gmail.com", "1***");

        try (
                SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
                Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(expectedUser);
            session.flush();
            session.clear();

            User actualUser = session.get(User.class, expectedUser.getId());
            System.out.println(actualUser);

            assertThat(expectedUser).isEqualTo(actualUser);

            session.getTransaction().rollback();
        }
    }

    @Test
    void getUser() {
        User expectedUser = buildRandomUser(87771112233L, "test@gmail.com", "***");

        try (
                SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
                Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(expectedUser);

            session.getTransaction().commit();
            session.clear();

            User actualUser = session.get(User.class, expectedUser.getId());
            System.out.println(actualUser);

            assertThat(expectedUser).isEqualTo(actualUser);
        }
    }

    private static User buildUser(Long phoneNumber, String email, String password) {
        User user = User.builder()
                .role(Role.ADMIN)
                .firstName("Ivan")
                .lastName("Ivanov")
                .phoneNumber(phoneNumber)
                .address("Earth")
                .age(22)
                .email(email)
                .password(password)
                .build();
        return user;
    }

    private static User buildRandomUser(Long phoneNumber, String email, String password) {
        Long random = new Random().nextLong(10000);
        User user = User.builder()
                .role(Role.ADMIN)
                .firstName("Ivan")
                .lastName("Ivanov")
                .phoneNumber(phoneNumber + random)
                .address("Earth")
                .age(22)
                .email(random + email)
                .password(password + random)
                .build();
        return user;
    }
}