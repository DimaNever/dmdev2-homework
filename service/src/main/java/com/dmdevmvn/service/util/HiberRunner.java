package com.dmdevmvn.service.util;

public class HiberRunner {

    public static void main(String[] args) {

        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
            TestDataImporter.importData(sessionFactory);
        }
    }
}
