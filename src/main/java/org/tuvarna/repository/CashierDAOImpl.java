package org.tuvarna.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
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
    public Cashier getCashierByName(String name) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        Cashier cashier = session.get(Cashier.class, name);
        session.getTransaction().commit();
        session.close();
        return cashier;

    }

    @Override
    public List<Cashier> getCompanies() {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Cashier> cashierList = session.createQuery("from Cashier").getResultList();
        session.getTransaction().commit();
        session.close();
        return cashierList;
    }

    @Override
    public void addCashier(Cashier Cashier) {
        sessionFactory.getCurrentSession().persist(Cashier);
    }

}
