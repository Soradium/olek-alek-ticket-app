package org.tuvarna.factories;

import org.tuvarna.database.DatabaseSingleton;
import org.tuvarna.entity.*;
import org.tuvarna.repository.*;

import java.util.Objects;

public class FactoryDAO {
    private static FactoryDAO instance;
    DatabaseSingleton database;
    TableDAO<Bus> busDao;
    TableDAO<Cashier> cashierDao;
    TableDAO<Company> companyDao;
    TableDAO<Distributor> distributorDao;
    TableDAO<Ticket> ticketDao;
    TableDAO<Trip> tripDao;
    TableDAO<User> userDao;

    private FactoryDAO() {
        database = DatabaseSingleton.getInstance();
    }

    public static FactoryDAO getInstance() {
        if (instance == null) {
            instance = new FactoryDAO();
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    public <T> TableDAO<T> getDao(Class<T> requiredDaoClass) {
        if (requiredDaoClass == Bus.class) {
            return (TableDAO<T>) Objects.requireNonNullElseGet(
                    busDao, () -> new BusDAOImpl(database.getSessionFactory()));
        } else if (requiredDaoClass == Cashier.class) {
            return (TableDAO<T>) Objects.requireNonNullElseGet(
                    cashierDao, () -> new CashierDAOImpl(database.getSessionFactory()));
        } else if (requiredDaoClass == Company.class) {
            return (TableDAO<T>) Objects.requireNonNullElseGet(
                    companyDao, () -> new CompanyDAOImpl(database.getSessionFactory()));
        } else if (requiredDaoClass == Distributor.class) {
            return (TableDAO<T>) Objects.requireNonNullElseGet(
                    distributorDao, () -> new DistributorDAOImpl(database.getSessionFactory()));
        } else if (requiredDaoClass == Ticket.class) {
            return (TableDAO<T>) Objects.requireNonNullElseGet(
                    ticketDao, () -> new TicketDAOImpl(database.getSessionFactory()));
        } else if (requiredDaoClass == Trip.class) {
            return (TableDAO<T>) Objects.requireNonNullElseGet(
                    tripDao, () -> new TripDAOImpl(database.getSessionFactory()));
        } else if (requiredDaoClass == User.class) {
            return (TableDAO<T>) Objects.requireNonNullElseGet(
                    userDao, () -> new UserDAOImpl(database.getSessionFactory()));
        }
        throw new IllegalArgumentException("Unsupported entity class: " + requiredDaoClass.getName());
    }

}
