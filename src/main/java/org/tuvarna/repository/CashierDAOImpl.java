package org.tuvarna.repository;

import org.hibernate.SessionFactory;

public class CashierDAOImpl {
    private SessionFactory sessionFactory;

    public CashierDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


}
