package com.dmdevmvn.service.util;

import com.dmdevmvn.service.entity.Car;
import com.dmdevmvn.service.entity.Client;
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
}
