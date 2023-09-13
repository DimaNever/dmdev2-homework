package com.dmdevmvn.dao;

import com.dmdevmvn.entity.Car;
import com.dmdevmvn.entity.Car_;
import com.dmdevmvn.entity.Client;
import com.dmdevmvn.entity.Client_;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import javax.persistence.criteria.JoinType;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CarDaoWithCriteria {

    private static final CarDaoWithCriteria INSTANCE = new CarDaoWithCriteria();

    public List<Car> findAll(Session session) {
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Car.class);
        var car = criteria.from(Car.class);

        criteria.select(car);

        return session.createQuery(criteria)
                .list();
    }

    public List<Car> findAllByModel(Session session, String model) {
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Car.class);
        var car = criteria.from(Car.class);

        criteria.select(car).where(
                cb.equal(car.get(Car_.MODEL), model)
        );

        return session.createQuery(criteria)
                .list();
    }

    public List<Car> findLimitedCarOrderByYear(Session session, int limit) {
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Car.class);
        var car = criteria.from(Car.class);

        criteria.select(car).orderBy(cb.asc(car.get(Car_.YEAR)));

        return session.createQuery(criteria)
                .setMaxResults(limit)
                .list();
    }

    public List<Car> findAllCarsByClientLastName(Session session, String clientLastName) {
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Car.class);
        var client = criteria.from(Client.class);
        var cars = client.join(Client_.cars);

        criteria.select(cars).where(
                cb.equal(client.get(Client_.lastName), clientLastName)
        );

        return session.createQuery(criteria)
                .list();
    }

    public List<Object[]> findClientLastNameWithAvgCarMileageOrderByClientLastName(Session session) {
        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Object[].class);
        var client = criteria.from(Client.class);
        var car = client.join(Client_.cars, JoinType.INNER);

        criteria.multiselect(
                        client.get(Client_.lastName),
                        cb.avg(car.get(Car_.year))
                )
                .groupBy(client.get(Client_.id))
                .orderBy(cb.asc(client.get(Client_.lastName)));

        return session.createQuery(criteria)
                .list();
    }

    public static CarDaoWithCriteria getInstance() {
        return INSTANCE;
    }
}
