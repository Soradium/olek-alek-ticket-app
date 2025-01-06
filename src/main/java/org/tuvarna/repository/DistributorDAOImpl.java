package org.tuvarna.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.entity.Distributor;

import java.util.List;

public class DistributorDAOImpl implements TableDAO<Distributor> {
    private final SessionFactory sessionFactory;

    public DistributorDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Distributor findById(int id) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            Distributor distributor = session.get(Distributor.class, id);
            session.getTransaction().commit();
            return distributor;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<Distributor> findAll() {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            List<Distributor> distributorList = session.createQuery("from Distributor", Distributor.class).getResultList();
            session.getTransaction().commit();
            return distributorList;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Distributor save(Distributor distributor) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            session.persist(distributor);
            session.getTransaction().commit();
            return distributor;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
}
