package org.tuvarna.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.entity.Distributor;

import java.util.List;

public class DistributorDAOImpl implements DistributorDAO {
    private final SessionFactory sessionFactory;

    public DistributorDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Distributor getDistributorByName(String name) {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        Distributor distributor = session.get(Distributor.class, name);
        session.getTransaction().commit();
        session.close();
        return distributor;

    }

    @Override
    public List<Distributor> getDistributors() {
        Session session = this.sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Distributor> distributorList = session.createQuery("from Distributor").getResultList();
        session.getTransaction().commit();
        session.close();
        return distributorList;
    }

    @Override
    public void addDistributor(Distributor distributor) {
        sessionFactory.getCurrentSession().persist(distributor);
    }
}
