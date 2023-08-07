package com.dmdevmvn.service.util;

import com.dmdevmvn.service.entity.Car;
import com.dmdevmvn.service.entity.Client;
import com.dmdevmvn.service.entity.Order;
import com.dmdevmvn.service.entity.OrderSpareParts;
import com.dmdevmvn.service.entity.Role;
import com.dmdevmvn.service.entity.SparePart;
import com.dmdevmvn.service.entity.User;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Random;

import static com.dmdevmvn.service.entity.ServiceType.MAINTENANCE;

@UtilityClass
public class EntityUtil {

    public static User buildRandomUser(String firstName, String lastName) {
        long random = new Random().nextLong(10000);
        return User.builder()
                .role(Role.ADMIN)
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
                .mileage(random * 100000)
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

    public static Order buildOrder(User user, Car car) {
        long random = new Random().nextLong(10000);
        return Order.builder()
                .serviceType(MAINTENANCE)
                .user(user)
                .car(car)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(7))
                .price(random)
                .build();
    }

    public static OrderSpareParts buildServiceSpareParts(Order order, SparePart sparePart) {
        Integer random = new Random().nextInt(100);
        return OrderSpareParts.builder()
                .order(order)
                .sparePart(sparePart)
                .quantity(random)
                .build();
    }
}
