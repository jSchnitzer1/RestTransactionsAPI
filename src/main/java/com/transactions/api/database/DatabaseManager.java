package com.transactions.api.database;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DatabaseManager {
    private static final Logger LOGGER = Logger.getLogger(DatabaseManager.class.getName());
    private static SessionFactory sessionFactory;
    private static Session session;
    private static DatabaseManager databaseManager;

    private DatabaseManager() {
    }

    public static DatabaseManager getInstance() {
        if (databaseManager == null) {
            LOGGER.info("Initialize DatabaseManager (Singleton)");
            databaseManager = new DatabaseManager();
        }
        return databaseManager;
    }

    /**
     * This method is creates a SessionFactory only and only if the connection to database is disrupted
     * or SessionFactory is closed or null
     */
    public void initDatabase() {
        if(sessionFactory == null || sessionFactory.isClosed()) {
            LOGGER.info("initDatabase is triggered.");
            sessionFactory = new Configuration().configure().buildSessionFactory();
            session = sessionFactory.openSession();
            LOGGER.info("initDatabase has initialized the database.");
        }
    }

    public void destroyDatabase() {
        if(sessionFactory == null || sessionFactory.isClosed()) {
            LOGGER.info("destroyDatabase is triggered.");
            session.close();
            sessionFactory.close();
            LOGGER.info("destroyDatabase has destroyed in-memory database.");
        }
    }
}
