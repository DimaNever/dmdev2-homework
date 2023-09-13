package com.dmdevmvn.service.dao;

import com.dmdevmvn.service.entity.Car;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dmdevmvn.service.entity.QCar.car;
import static com.dmdevmvn.service.entity.QClient.client;

public class CarRepository extends RepositoryBase<Long, Car> {

    public CarRepository(EntityManager entityManager) {
        super(Car.class, entityManager);
    }

    public List<Car> findAllCarsByModel(String model) {
        return new JPAQuery<Car>(getEntityManager())
                .select(car)
                .from(car)
                .where(car.model.eq(model))
                .fetch();
    }

    public List<Car> findAllCarsByClientId(Long clientId) {
        return new JPAQuery<Car>(getEntityManager())
                .select(car)
                .from(car)
                .join(car.client, client)
                .where(client.id.eq(clientId))
                .fetch();
    }
}
