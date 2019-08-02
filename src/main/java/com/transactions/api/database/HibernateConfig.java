package com.transactions.api.database;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConfig {

    public static SessionFactory buildSessionFactory(Class fromClass) {
        return new Configuration().configure().addAnnotatedClass(fromClass).buildSessionFactory();
    }
}
