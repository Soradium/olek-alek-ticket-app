package org.tuvarna.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.entity.Trip;

import java.util.List;

public class TripDAOImpl implements TripDAO {

    private static SessionFactory sessionFactory;

    public TripDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Trip> allTrips() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            List<Trip> allTrips = session.createQuery("from Trip", Trip.class).getResultList();
            return allTrips;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            session.getTransaction().commit();
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
            e.printStackTrace();
        }
        return null;
    }
}
