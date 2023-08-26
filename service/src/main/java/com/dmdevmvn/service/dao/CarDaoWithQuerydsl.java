package com.dmdevmvn.service.dao;

import com.dmdevmvn.service.dto.CarFilter;
import com.dmdevmvn.service.entity.Car;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

import static com.dmdevmvn.service.entity.QCar.car;
import static com.dmdevmvn.service.entity.QClient.client;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CarDaoWithQuerydsl {

    private static final CarDaoWithQuerydsl INSTANCE = new CarDaoWithQuerydsl();

    public List<Car> findAll(Session session) {
        return new JPAQuery<Car>(session)
                .select(car)
                .from(car)
                .fetch();
    }

    public List<Car> findAllByModel(Session session, String model) {
        return new JPAQuery<Car>(session)
                .select(car)
                .from(car)
                .where(car.model.eq(model))
                .fetch();
    }

    public List<Car> findLimitedCarOrderByYear(Session session, int limit) {
        return new JPAQuery<Car>(session)
                .select(car)
                .from(car)
                .orderBy(car.year.asc())
                .limit(limit)
                .fetch();
    }

    public List<Car> findAllCarsByClientLastName(Session session, String clientLastName) {
        return new JPAQuery<Car>(session)
                .select(car)
                .from(client)
                .join(client.cars, car)
                .where(client.lastName.eq(clientLastName))
                .fetch();
    }

    public List<Tuple> findClientLastNameWithAvgCarMileageOrderByClientLastName(Session session) {
        return new JPAQuery<Tuple>(session)
                .select(client.lastName, car.mileage.avg())
                .from(client)
                .join(client.cars, car)
                .groupBy(client.lastName)
                .orderBy(client.lastName.asc())
                .fetch();
    }

    public List<Car> findAllCarsByClientLastNameWithFilter(Session session, CarFilter filter) {
        var predicate = QPredicate.builder()
                .add(filter.getLastName(), car.client.lastName::eq)
                .add(filter.getFirstName(), car.client.firstName::eq)
                .buildAnd();

        return new JPAQuery<Car>(session)
                .select(car)
                .from(client)
                .join(client.cars, car)
                .where(predicate)
                .fetch();
    }

    public static CarDaoWithQuerydsl getInstance() {
        return INSTANCE;
    }
}
