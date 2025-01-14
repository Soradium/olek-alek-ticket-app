package org.tuvarna.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.entity.Cashier;

import java.util.List;

public class CashierDAOImpl implements TableDAO<Cashier> {

    private final SessionFactory sessionFactory;

    public CashierDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private final static Logger logger = LogManager.getLogger(CashierDAOImpl.class);

    @Override
    public Cashier findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for findById");
        try {
            Cashier cashier = session.get(Cashier.class, id);
            logger.info("Cashier with id {} fetched successfully", id);
            session.getTransaction().commit();
            logger.info("Transaction commited successfully");
            return cashier;
        } catch (Exception e) {
            logger.error("Occurred error in findById method, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rollbacked successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }

    @Override
    public List<Cashier> findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for findAll");
        try {
            List<Cashier> cashierList = session.createQuery("from Cashier", Cashier.class).getResultList();
            logger.info("Cashiers fetched successfully: {}", cashierList.size());
            session.getTransaction().commit();
            logger.info("Transaction commited successfully");
            return cashierList;
        } catch (Exception e) {
            logger.error("Occurred error in findAll method, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rollbacked successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }

    @Override
    public Cashier save(Cashier cashier) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for save");
        try {
            session.persist(cashier);
            logger.info("Cashier {} saved successfully", cashier);
            session.getTransaction().commit();
            logger.info("Transaction commited successfully");
            return cashier;
        } catch (Exception e) {
            logger.error("Occurred error in save method, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rollbacked successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }

    @Override
    public Cashier update(Cashier cashier) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for update");
        try {
            session.merge(cashier);
            logger.info("Cashier {} updated successfully", cashier);
            session.getTransaction().commit();
            logger.info("Transaction commited successfully");
            return cashier;
        } catch (Exception e) {
            logger.error("Occurred error in update method, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rollbacked successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }
}
