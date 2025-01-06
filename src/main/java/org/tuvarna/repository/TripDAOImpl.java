package org.tuvarna.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.entity.Trip;

import java.util.List;

public class TripDAOImpl implements TripDAO {

    private final SessionFactory sessionFactory;

    public TripDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Trip getTripById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            Trip trip = session.get(Trip.class, id);
            session.getTransaction().commit();
            return trip;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<Trip> getAllTrips() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            List<Trip> allTrips = session.createQuery("from Trip", Trip.class).getResultList();
            session.getTransaction().commit();
            return allTrips;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public Trip addTrip(Trip trip) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try{
            session.persist(trip);
            session.getTransaction().commit();
            return trip;
        }catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }
}
