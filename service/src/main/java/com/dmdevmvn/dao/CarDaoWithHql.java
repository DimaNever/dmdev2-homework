package com.dmdevmvn.dao;

import com.dmdevmvn.entity.Car;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CarDaoWithHql {

    private static final CarDaoWithHql INSTANCE = new CarDaoWithHql();

    public List<Car> findAll(Session session) {
        return session.createQuery("select c from Car c", Car.class)
                .list();
    }

    public List<Car> findAllByModel(Session session, String model) {
        return session.createQuery("select c from Car c " +
                                   "where c.model = :model", Car.class)
                .setParameter("model", model)
                .list();
    }

    public List<Car> findLimitedCarOrderByYear(Session session, int limit) {
        return session.createQuery("select c from Car c order by c.year", Car.class)
                .setMaxResults(limit)
                .list();
    }

    public List<Car> findAllCarsByClientLastName(Session session, String clientLastName) {
        return session.createQuery("select car from Client cl " +
                                   "join cl.cars car " +
                                   "where cl.lastName = :clientLastName", Car.class)
                .setParameter("clientLastName", clientLastName)
                .list();
    }

    public List<Object[]> findClientLastNameWithAvgCarMileageOrderByClientLastName(Session session) {
        return session.createQuery("select cl.lastName, avg (car.mileage) from Client cl " +
                                   "join cl.cars car " +
                                   "group by cl.id " +
                                   "order by cl.lastName", Object[].class)
                .list();
    }

    public static CarDaoWithHql getInstance() {
        return INSTANCE;
    }
}
