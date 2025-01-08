package org.tuvarna.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.entity.Trip;

import java.util.List;

public class TripDAOImpl implements TableDAO<Trip> {

    private final SessionFactory sessionFactory;

    public TripDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Trip findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            Trip trip = session.get(Trip.class, id);
            session.getTransaction().commit();
            return trip;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<Trip> findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            List<Trip> allTrips = session.createQuery("from Trip", Trip.class).getResultList();
            session.getTransaction().commit();
            return allTrips;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Trip save(Trip trip) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            Trip currentTrip = session.merge(trip);
            session.getTransaction().commit();
            return currentTrip;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Trip update(Trip trip) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            session.merge(trip);
            session.getTransaction().commit();
            return trip;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
}
