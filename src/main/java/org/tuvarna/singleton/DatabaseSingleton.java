package org.tuvarna.singleton;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.tuvarna.entity.*;

public class DatabaseSingleton {
    private static DatabaseSingleton instance;
    private final SessionFactory sessionFactory;

    private DatabaseSingleton() {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Trip.class)
                .addAnnotatedClass(Company.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Ticket.class)
                .addAnnotatedClass(Bus.class)
                .addAnnotatedClass(Distributor.class)
                .addAnnotatedClass(Seat.class)
                .addAnnotatedClass(Cashier.class)
                .buildSessionFactory();
    }

    public static DatabaseSingleton getInstance() {
        if (instance == null) {
            instance = new DatabaseSingleton();
        }
        return instance;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
