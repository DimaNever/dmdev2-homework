package com.dmdevmvn.util;

import com.dmdevmvn.service.entity.Car;
import com.dmdevmvn.service.entity.Client;
import com.dmdevmvn.service.entity.Order;
import com.dmdevmvn.service.entity.OrderSpareParts;
import com.dmdevmvn.service.entity.Role;
import com.dmdevmvn.service.entity.ServiceType;
import com.dmdevmvn.service.entity.SparePart;
import com.dmdevmvn.service.entity.User;
import com.dmdevmvn.service.util.EntityUtil;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@UtilityClass
public class TestDataImporter {

    public void importData(SessionFactory sessionFactory) {
        @Cleanup Session session = sessionFactory.openSession();

        Client ivanIvanov = saveClient(session, "Ivan", "Ivanov");
        Client petrPetrov = saveClient(session, "Petr", "Petrov");
        Client testTestov = saveClient(session, "Test", "ATestov");

        Car carOfIvanov = saveCar(session, "Audi", ivanIvanov);
        Car carOfIvanov2 = saveCar(session, "Lada", ivanIvanov);
        Car carOfPetrov = saveCar(session, "Audi", petrPetrov);
        Car carOfPetrov2 = saveCar(session, "Ford", petrPetrov);
        Car carOfTestov = saveCar(session, "Nissan", testTestov);

        User admin = saveUser(session, "Admin", "Adminov", Role.ADMIN);
        User slesar = saveUser(session, "Slesar", "Slesarev", Role.WORKER);
        User motorist = saveUser(session, "Motorist", "Motoristov", Role.WORKER);

        Order diagnostics1 = saveOrder(session, admin, carOfIvanov, ServiceType.DIAGNOSTICS);
        Order maintenance1 = saveOrder(session, slesar, carOfIvanov, ServiceType.MAINTENANCE);
        Order repair1 = saveOrder(session, motorist, carOfIvanov, ServiceType.REPAIR);
        Order diagnostics2 = saveOrder(session, admin, carOfIvanov2, ServiceType.DIAGNOSTICS);
        Order diagnostics3 = saveOrder(session, admin, carOfPetrov, ServiceType.DIAGNOSTICS);
        Order maintenance2 = saveOrder(session, slesar, carOfPetrov2, ServiceType.MAINTENANCE);
        Order repair2 = saveOrder(session, motorist, carOfTestov, ServiceType.REPAIR);

        SparePart sparePart1 = saveSparePart(session);
        SparePart sparePart2 = saveSparePart(session);
        SparePart sparePart3 = saveSparePart(session);
        SparePart sparePart4 = saveSparePart(session);
        SparePart sparePart5 = saveSparePart(session);
        SparePart sparePart6 = saveSparePart(session);
        SparePart sparePart7 = saveSparePart(session);

        OrderSpareParts orderSpareParts1 = saveOrderSpareParts(session, maintenance1, sparePart1);
        OrderSpareParts orderSpareParts2 = saveOrderSpareParts(session, repair1, sparePart2);
        OrderSpareParts orderSpareParts3 = saveOrderSpareParts(session, repair1, sparePart3);
        OrderSpareParts orderSpareParts4 = saveOrderSpareParts(session, repair2, sparePart4);
        OrderSpareParts orderSpareParts5 = saveOrderSpareParts(session, repair2, sparePart5);
        OrderSpareParts orderSpareParts6 = saveOrderSpareParts(session, maintenance1, sparePart6);
        OrderSpareParts orderSpareParts7 = saveOrderSpareParts(session, maintenance2, sparePart7);
    }

    private Client saveClient(Session session, String firstName, String lastName) {
        Client client = EntityUtil.buildRandomClient(firstName, lastName);
        session.save(client);
        return client;
    }

    private Car saveCar(Session session, String model, Client client) {
        Car car = EntityUtil.buildCar(model, client);
        session.save(car);
        return car;
    }

    private User saveUser(Session session, String firstName, String lastName, Role role) {
        User user = EntityUtil.buildRandomUser(firstName, lastName, role);
        session.save(user);
        return user;
    }

    private Order saveOrder(Session session, User user, Car car, ServiceType serviceType) {
        Order order = EntityUtil.buildOrder(user, car, serviceType);
        session.save(order);
        return order;
    }

    private SparePart saveSparePart(Session session) {
        SparePart sparePart = EntityUtil.buildRandomSparePart();
        session.save(sparePart);
        return sparePart;
    }

    private OrderSpareParts saveOrderSpareParts(Session session, Order order, SparePart sparePart) {
        OrderSpareParts orderSpareParts = EntityUtil.buildOrderSpareParts(order, sparePart);
        session.save(orderSpareParts);
        return orderSpareParts;
    }
}