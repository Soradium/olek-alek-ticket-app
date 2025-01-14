package org.tuvarna.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.entity.Trip;

import java.util.List;

public class TripDAOImpl implements TableDAO<Trip> {

    private final SessionFactory sessionFactory;

    public TripDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private final static Logger logger = LogManager.getLogger(TripDAOImpl.class);

    @Override
    public Trip findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for findById");
        try {
            Trip trip = session.get(Trip.class, id);
            logger.info("Trip with id {} fetched successfully", id);
            session.getTransaction().commit();
            logger.info("Transaction committed successfully");
            return trip;
        } catch (Exception e) {
            logger.error("Occurred with error in findByAll method, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rollback successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }

    @Override
    public List<Trip> findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for findAll");
        try {
            List<Trip> allTrips = session.createQuery("from Trip", Trip.class).getResultList();
            logger.info("Trips fetched successfully: {}", allTrips.size());
            session.getTransaction().commit();
            logger.info("Transaction committed successfully");
            return allTrips;
        } catch (Exception e) {
            logger.error("Occurred with error in findAll method, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rollback successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }

    @Override
    public Trip save(Trip trip) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for save");
        try {
            Trip currentTrip = session.merge(trip);
            logger.info("Trip {} saved successfully", trip);
            session.getTransaction().commit();
            logger.info("Transaction committed successfully");
            return currentTrip;
        } catch (Exception e) {
            logger.error("Occurred with error in save method, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rollback successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }

    @Override
    public Trip update(Trip trip) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for update");
        try {
            session.merge(trip);
            logger.info("Ticket {} updated successfully", trip);
            session.getTransaction().commit();
            logger.info("Transaction committed successfully");
            return trip;
        } catch (Exception e) {
            logger.error("Occurred with error in update method, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rollback successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }
}
