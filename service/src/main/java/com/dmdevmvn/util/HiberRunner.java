package com.dmdevmvn.util;

import com.dmdevmvn.dao.SparePartRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.Proxy;

public class HiberRunner {

    public static void main(String[] args) {

//        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
//            TestDataImporter.importData(sessionFactory);
//        }

//        try (var sessionFactory = HibernateUtil.buildSessionFactory()) {
//            var session = (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
//                    (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
//
//            session.beginTransaction();
//
//            var sparePartRepository = new SparePartRepository(session);
//            var allSparePartsByOrderId = sparePartRepository.findAllSparePartsByOrderId(2L);

//            var carRepository = new CarRepository(session);
//            carRepository.findByID(1L).ifPresent(System.out::println);
//
//            var allByModel = carRepository.findAllByModel("Audi");
//            System.out.println(allByModel);
//
//            var allCarsByClientId = carRepository.findAllCarsByClientId(1L);
//            System.out.println(allCarsByClientId);
//
//
//            var clientRepository = new ClientRepository(session);
//            clientRepository.findByID(3L).ifPresent(System.out::println);

//            var orderRepository = new OrderRepository(session);
//            var allOrdersByCarId = orderRepository.findAllOrdersByCarId(5L);


//            var clientRepository = new ClientRepository(session);
//
//            var clientReadMapper = new ClientReadMapper();
//            var carReadMapper = new CarReadMapper(clientReadMapper);
//            var carCreateMapper = new CarCreateMapper(clientRepository);
//
//            var carRepository = new CarRepository(session);
//            var carService = new CarService(carRepository, carReadMapper, carCreateMapper);
//
//            carService.findById(1L).ifPresent(System.out::println);
//
//            CarCreateDto carCreateDto = new CarCreateDto(
//                    "DTO",
//                    2023,
//                    1000L,
//                    3L
//            );
//            carService.create(carCreateDto);

//            session.getTransaction().commit();
        }
    }
//}
