package com.dmdevmvn.dao;

import com.dmdevmvn.entity.Car;
import com.dmdevmvn.entity.Client;
import com.dmdevmvn.entity.Order;
import com.dmdevmvn.entity.Role;
import com.dmdevmvn.entity.ServiceType;
import com.dmdevmvn.entity.User;
import com.dmdevmvn.util.TestBase;
import org.junit.jupiter.api.Test;

import static com.dmdevmvn.util.EntityUtil.buildCar;
import static com.dmdevmvn.util.EntityUtil.buildOrder;
import static com.dmdevmvn.util.EntityUtil.buildRandomClient;
import static com.dmdevmvn.util.EntityUtil.buildRandomUser;
import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrderRepositoryTest extends TestBase {

    private final OrderRepository orderRepository = context.getBean(OrderRepository.class);

    @Test
    void save() {
        User expectedUser = buildRandomUser("Save", "User", Role.ADMIN);
        Client expectedClient = buildRandomClient("Save", "Client");
        Car expectedCar = buildCar("BMW", expectedClient);
        Order expectedOrder = buildOrder(expectedUser, expectedCar, ServiceType.MAINTENANCE);

        session.save(expectedUser);
        session.save(expectedClient);
        session.save(expectedCar);
        orderRepository.save(expectedOrder);
        session.clear();

        var optionalOrder = orderRepository.findById(expectedOrder.getId());
        assertNotNull(optionalOrder);
    }

    @Test
    void delete() {
        User expectedUser = buildRandomUser("Save", "User", Role.ADMIN);
        Client expectedClient = buildRandomClient("Save", "Client");
        Car expectedCar = buildCar("BMW", expectedClient);
        Order expectedOrder = buildOrder(expectedUser, expectedCar, ServiceType.MAINTENANCE);

        session.save(expectedUser);
        session.save(expectedClient);
        session.save(expectedCar);
        orderRepository.save(expectedOrder);

        orderRepository.delete(expectedOrder);

        session.flush();
        session.clear();

        assertTrue(orderRepository.findById(expectedOrder.getId()).isEmpty());
    }

    @Test
    void update() {
        User expectedUser = buildRandomUser("Save", "User", Role.ADMIN);
        Client expectedClient = buildRandomClient("Save", "Client");
        Car expectedCar = buildCar("BMW", expectedClient);
        Order expectedOrder = buildOrder(expectedUser, expectedCar, ServiceType.MAINTENANCE);

        session.save(expectedUser);
        session.save(expectedClient);
        session.save(expectedCar);
        orderRepository.save(expectedOrder);

        expectedOrder.setServiceType(ServiceType.REPAIR);
        orderRepository.update(expectedOrder);

        session.flush();
        session.clear();

        var optionalOrder = orderRepository.findById(expectedOrder.getId());

        assertTrue(optionalOrder.isPresent());
        assertEquals(ServiceType.REPAIR, optionalOrder.get().getServiceType());
    }

    @Test
    void findById() {
        var optionalOrder = orderRepository.findById(1L);

        assertNotNull(optionalOrder);
    }

    @Test
    void findAll() {
        var orderRepositoryAll = orderRepository.findAll();

        assertThat(orderRepositoryAll).hasSize(7);
    }

    @Test
    void findAllOrdersByCarId() {
        var allOrdersByCarId = orderRepository.findAllOrdersByCarId(1L);

        assertThat(allOrdersByCarId).hasSize(3);
    }

    @Test
    void findAllOrdersByClientId() {
        var allOrdersByClientId = orderRepository.findAllOrdersByClientId(1L);
        var modelCars = allOrdersByClientId.stream()
                .map(Order::getCar)
                .map(Car::getModel)
                .collect(toSet());

        assertThat(allOrdersByClientId).hasSize(4);
        assertThat(modelCars).containsExactlyInAnyOrder("Lada", "Audi");
    }
}