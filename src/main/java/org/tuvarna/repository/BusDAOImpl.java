package org.tuvarna.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.entity.Bus;

import java.util.List;

public class BusDAOImpl implements TableDAO<Bus> {

    private final SessionFactory sessionFactory;

    public BusDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private final static Logger logger = LogManager.getLogger(BusDAOImpl.class);

    @Override
    public Bus findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Transaction started for findByID");
        try {
            Bus bus = session.get(Bus.class, id);
            logger.info("Bus with id {} fetched successfully", id);
            session.getTransaction().commit();
            logger.info("Transaction commited successfully");
            return bus;
        } catch (Exception e) {
            logger.error("Occurred error in findById method with error: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rolled back successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }

    @Override
    public List<Bus> findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Transaction started for findAll");
        try {
            List<Bus> buses = session.createQuery("from Bus", Bus.class).getResultList();
            logger.info("Buses fetched successfully");
            session.getTransaction().commit();
            logger.info("Transaction commited successfully");
            return buses;
        } catch (Exception e) {
            logger.error("Occurred error in findAll method with error: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rolled back successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }

    @Override
    public Bus save(Bus bus) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Transaction started for save");
        try {
            session.merge(bus);
            logger.info("Bus {} saved successfully", bus);
            session.getTransaction().commit();
            logger.info("Transaction commited successfully");
            return bus;
        } catch (Exception e) {
            logger.error("Occurred error in save method with error: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rolled back successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }

    @Override
    public Bus update(Bus bus) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Transaction started for update");
        try {
            session.merge(bus);
            logger.info("Bus {} updated successfully", bus);
            session.getTransaction().commit();
            logger.info("Transaction commited successfully");
            return bus;
        } catch (Exception e) {
            logger.error("Occurred error in update method with error: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rolled back successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }
}
