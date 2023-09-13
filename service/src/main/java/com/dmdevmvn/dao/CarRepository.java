package com.dmdevmvn.dao;

import com.dmdevmvn.entity.Car;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dmdevmvn.entity.QCar.car;
import static com.dmdevmvn.entity.QClient.client;

@Repository
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
