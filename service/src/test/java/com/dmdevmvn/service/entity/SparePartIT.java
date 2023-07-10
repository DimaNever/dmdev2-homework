package com.dmdevmvn.service.entity;

import com.dmdevmvn.service.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class SparePartIT {
    @Test
    void saveSparePart() {

        SparePart expectedSparePart = buildRandomSparePart();

        try (
                SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
                Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(expectedSparePart);
            session.flush();
            session.clear();

            SparePart actualSparePart = session.get(SparePart.class, expectedSparePart.getId());
            System.out.println(actualSparePart);

            assertThat(expectedSparePart).isEqualTo(actualSparePart);

            session.getTransaction().rollback();
        }
    }

    @Test
    void getSparePart() {
        SparePart expectedSparePart = buildRandomSparePart();

        try (
                SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
                Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.save(expectedSparePart);

            session.getTransaction().commit();
            session.clear();
            SparePart actualSparePart = session.get(SparePart.class, expectedSparePart.getId());
            System.out.println(actualSparePart);

            assertThat(expectedSparePart).isEqualTo(actualSparePart);
        }
    }

    private static SparePart buildRandomSparePart() {
        Long random = new Random().nextLong(10000);
        SparePart sparePart = SparePart.builder()
                .vendorCode(String.valueOf(random))
                .title("part#" + random)
                .price(random * 10)
                .build();
        return sparePart;
    }
}