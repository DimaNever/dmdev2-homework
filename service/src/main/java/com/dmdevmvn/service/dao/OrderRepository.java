package com.dmdevmvn.service.dao;

import com.dmdevmvn.service.entity.Car;
import com.dmdevmvn.service.entity.Order;
import com.querydsl.jpa.impl.JPAQuery;

import javax.persistence.EntityManager;
import java.util.List;

import static com.dmdevmvn.service.entity.QCar.car;
import static com.dmdevmvn.service.entity.QOrder.order;
import static com.dmdevmvn.service.entity.QUser.user;

public class OrderRepository extends RepositoryBase<Long, Order> {

    public OrderRepository(EntityManager entityManager) {
        super(Order.class, entityManager);
    }

    public List<Order> findAllOrdersByCarId(Long carId) {
        return new JPAQuery<Car>(getEntityManager())
                .select(order)
                .from(order)
                .join(order.user, user)
                .join(order.car, car)
                .where(car.id.eq(carId))
                .fetch();
    }

    public List<Order> findAllOrdersByClientId(Long clientId) {
        return new JPAQuery<Car>(getEntityManager())
                .select(order)
                .from(order)
                .join(order.car, car)
                .where(car.client.id.eq(clientId))
                .fetch();
    }
}
