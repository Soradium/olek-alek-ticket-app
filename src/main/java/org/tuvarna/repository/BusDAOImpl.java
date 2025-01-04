package org.tuvarna.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.entity.Bus;

import java.util.List;

public class BusDAOImpl implements BusDAO {
    private SessionFactory sessionFactory;

    @Override
    public Bus getBusById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Bus bus = session.get(Bus.class, id);
        session.getTransaction().commit();
        return bus;
    }

    @Override
    public List<Bus> getBuses() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Bus> buses = session.createQuery("from Bus").getResultList();
        session.getTransaction().commit();
        session.close();
        return buses;
    }

    @Override
    public void addBus(Bus bus) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.persist(bus);
        session.getTransaction().commit();
        session.close();
    }
}
