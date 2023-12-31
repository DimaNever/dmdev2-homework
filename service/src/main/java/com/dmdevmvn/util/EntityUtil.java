package com.dmdevmvn.util;


import com.dmdevmvn.entity.Car;
import com.dmdevmvn.entity.Client;
import com.dmdevmvn.entity.Order;
import com.dmdevmvn.entity.OrderSpareParts;
import com.dmdevmvn.entity.Role;
import com.dmdevmvn.entity.ServiceType;
import com.dmdevmvn.entity.SparePart;
import com.dmdevmvn.entity.User;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Random;

@UtilityClass
public class EntityUtil {

    public static User buildRandomUser(String firstName, String lastName, Role role) {
        long random = new Random().nextLong(10000);
        return User.builder()
                .role(role)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(87771112233L + random)
                .address("Earth")
                .age(22)
                .email(random + "test@gmail.com")
                .password("***" + random)
                .build();
    }

    public static Client buildRandomClient(String firstName, String lastName) {
        long random = new Random().nextLong(10000);
        return Client.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(81112223344L + random)
                .email(random + "test@gmail.com")
                .build();
    }

    public static Car buildCar(String model, Client client) {
        long random = new Random().nextLong(33);
        return Car.builder()
                .model(model)
                .year((int) (1990 + random))
                .mileage(random * 10000)
                .client(client)
                .build();
    }

    public static SparePart buildRandomSparePart() {
        long random = new Random().nextLong(10000);
        return SparePart.builder()
                .vendorCode(String.valueOf(random))
                .title("part#" + random)
                .price(random * 10)
                .build();
    }

    public static Order buildOrder(User user, Car car, ServiceType serviceType) {
        long random = new Random().nextLong(10000);
        return Order.builder()
                .serviceType(serviceType)
                .user(user)
                .car(car)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(7))
                .price(random)
                .build();
    }

    public static OrderSpareParts buildOrderSpareParts(Order order, SparePart sparePart) {
        Integer random = new Random().nextInt(100);
        return OrderSpareParts.builder()
                .order(order)
                .sparePart(sparePart)
                .quantity(random)
                .build();
    }
}
