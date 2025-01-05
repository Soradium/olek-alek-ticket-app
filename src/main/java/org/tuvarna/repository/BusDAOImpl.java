package org.tuvarna.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.entity.Bus;

import java.util.List;

public class BusDAOImpl implements BusDAO {
    private final SessionFactory sessionFactory;

    public BusDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Bus getBusById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            Bus bus = session.get(Bus.class, id);
            session.getTransaction().commit();
            return bus;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<Bus> getBuses() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            List<Bus> buses = session.createQuery("from Bus", Bus.class).getResultList();
            session.getTransaction().commit();
            return buses;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Bus addBus(Bus bus) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            session.persist(bus);
            session.getTransaction().commit();
            return bus;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
}
