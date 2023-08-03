package com.dmdevmvn.service.util;

import com.dmdevmvn.service.entity.Car;
import com.dmdevmvn.service.entity.Client;
import com.dmdevmvn.service.entity.Role;
import com.dmdevmvn.service.entity.Service;
import com.dmdevmvn.service.entity.ServiceSpareParts;
import com.dmdevmvn.service.entity.SparePart;
import com.dmdevmvn.service.entity.User;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.util.Random;

import static com.dmdevmvn.service.entity.TypeService.MAINTENANCE;

@UtilityClass
public class EntityUtil {
    public static User buildRandomUser(String firstName, String lastName) {
        Long random = new Random().nextLong(10000);
        User user = User.builder()
                .role(Role.ADMIN)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(87771112233L + random)
                .address("Earth")
                .age(22)
                .email(random + "test@gmail.com")
                .password("***" + random)
                .build();
        return user;
    }

    public static Client buildRandomClient(String firstName, String lastName) {
        Long random = new Random().nextLong(10000);
        Client client = Client.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(81112223344L + random)
                .email(random + "test@gmail.com")
                .build();
        return client;
    }

    public static Car buildCar(String model, Client client) {
        Long random = new Random().nextLong(33);
        Car car = Car.builder()
                .model(model)
                .year((int) (1990 + random))
                .mileage(random * 100000)
                .client(client)
                .build();
        return car;
    }

    public static SparePart buildRandomSparePart() {
        Long random = new Random().nextLong(10000);
        SparePart sparePart = SparePart.builder()
                .vendorCode(String.valueOf(random))
                .title("part#" + random)
                .price(random * 10)
                .build();
        return sparePart;
    }

    public static Service buildService(User user, Car car) {
        Long random = new Random().nextLong(10000);
        Service service = Service.builder()
                .typeService(MAINTENANCE)
                .user(user)
                .car(car)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(7))
                .price(random)
                .build();
        return service;
    }

    public static ServiceSpareParts buildServiceSpareParts(Service service, SparePart sparePart) {
        Integer random = new Random().nextInt(100);
        ServiceSpareParts serviceSpareParts = ServiceSpareParts.builder()
                .service(service)
                .sparePart(sparePart)
                .quantity(random)
                .build();
        return serviceSpareParts;
    }
}
