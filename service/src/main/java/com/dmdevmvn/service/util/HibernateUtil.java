package com.dmdevmvn.service.util;

import com.dmdevmvn.service.entity.Car;
import com.dmdevmvn.service.entity.Client;
import com.dmdevmvn.service.entity.Service;
import com.dmdevmvn.service.entity.SparePart;
import com.dmdevmvn.service.entity.User;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Client.class);
        configuration.addAnnotatedClass(Car.class);
//        configuration.addAnnotatedClass(Service.class);
        configuration.addAnnotatedClass(SparePart.class);

        configuration.configure();

        return configuration.buildSessionFactory();
    }
}
