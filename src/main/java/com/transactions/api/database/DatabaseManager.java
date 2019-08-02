package com.transactions.api.database;

import com.transactions.api.model.dto.AccountDTO;
import com.transactions.api.model.dto.AccountsTransactionsDTO;
import com.transactions.api.model.dto.CustomerDTO;
import com.transactions.api.model.entity.Account;
import com.transactions.api.model.entity.Transaction;
import com.transactions.api.model.entity.Customer;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public boolean createTestDatabase() {
        LOGGER.info("createTestDatabase is triggered");
        List<Customer> customers = Stream
                .of(new Customer("Khaled", "Jendi"), new Customer("Tina", "John"), new Customer("Mat", "Olof"))
                .collect(Collectors.toCollection(ArrayList::new));

        org.hibernate.Transaction tx = null;
        try {
            tx = session.beginTransaction();

            //saving all new customers
            customers.forEach(c -> session.save(c));

            //creating an account for the first custoemr:
            Account kAccount = new Account(5000, customers.get(0));
            Account kAccount2 = new Account(9000, customers.get(0));
            session.save(kAccount);
            session.save(kAccount2);

            tx.commit();
            LOGGER.info("createTestDatabase - new customers and accounts are added successfully.");
            return true;
        } catch (Exception e) {
            if(tx != null) tx.rollback();
            LOGGER.error("createTestDatabase - error in initializing database: ", e);
            return false;
        }
    }

    public AccountsTransactionsDTO testDatabase() {
        LOGGER.info("testDatabase is triggered.");
        AccountsTransactionsDTO accountsTransactionsDTO = null;
        try {
            List<Account> accounts = session.createQuery("from Account a where a.customer.firstName = 'Khaled' and a.customer.lastName = 'Jendi'").list();
            if(accounts != null && accounts.size() > 0) {
                accountsTransactionsDTO = new AccountsTransactionsDTO();
                accountsTransactionsDTO.setAccountDTOs(AccountDTO.cloneFromEntity(accounts));
                accountsTransactionsDTO.setCustomerDTO(CustomerDTO.cloneFromEntity(accounts.get(0).getCustomer()));
                LOGGER.info("testDatabase - passed successfully.");
                return accountsTransactionsDTO;
            }
            LOGGER.info("testDatabase - unable to fetch customers and associated accounts.");
            return null;
        } catch (Exception e) {
            LOGGER.info("testDatabase - Error in listing accounts. ", e);
            return null;
        }
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
