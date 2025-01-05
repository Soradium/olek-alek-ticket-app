package org.tuvarna.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.entity.Cashier;

import java.util.List;

public class CashierDAOImpl implements CashierDAO {
    private final SessionFactory sessionFactory;

    public CashierDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Cashier getCashierById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            Cashier cashier = session.get(Cashier.class, id);
            session.getTransaction().commit();
            return cashier;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<Cashier> getCashiers() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            List<Cashier> cashierList = session.createQuery("from Cashier", Cashier.class).getResultList();
            session.getTransaction().commit();
            return cashierList;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Cashier addCashier(Cashier cashier) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            session.persist(cashier);
            session.getTransaction().commit();
            return cashier;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
}
