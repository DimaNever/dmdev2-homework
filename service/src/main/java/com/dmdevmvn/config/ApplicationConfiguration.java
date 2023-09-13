package com.dmdevmvn.config;

import com.dmdevmvn.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

import java.lang.reflect.Proxy;

@Configuration
@ComponentScan(basePackages = "com.dmdevmvn")
public class ApplicationConfiguration {

    @Bean
    public Session session(SessionFactory sessionFactory) {
        return (Session) Proxy.newProxyInstance(SessionFactory.class.getClassLoader(), new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1));
    }

    @Bean
    SessionFactory sessionFactory() {
        return HibernateUtil.buildSessionFactory();
    }

    @PreDestroy
    public void closeResources() {
        sessionFactory().close();
    }
}
