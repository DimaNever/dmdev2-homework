package com.dmdevmvn.service;

import com.dmdevmvn.service.entity.Role;
import com.dmdevmvn.service.entity.User;
import com.dmdevmvn.service.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;


@Slf4j
public class HibernateRunner {

    public static void main(String[] args) {
        User user = User.builder()

                .role(Role.ADMIN)
                .firstName("Ivan")
                .lastName("Ivanov")
                .phoneNumber(27771112233L)
                .address("Earth")
                .age(55)
                .email("test2@gmail.com")
                .password("2***")
                .build();
        log.info("User in transient state, object: {}", user);


        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(user);

            session.getTransaction().commit();
        }
    }
}
