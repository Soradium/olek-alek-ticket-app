package org.tuvarna.repository;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tuvarna.entity.Bus;
import org.tuvarna.entity.Company;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BusDAOImplTst {
    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    @Mock
    private BusDAOImpl busDAO;

    @Mock
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        when(sessionFactory.getCurrentSession()).thenReturn(session);

        when(session.getTransaction()).thenReturn(transaction);

        busDAO = new BusDAOImpl(sessionFactory);
    }

    @Test
    void testSaveBus() {
        Company company = new Company("New company");
        Bus bus = new Bus(company);

        busDAO.save(bus);

        verify(session).persist(bus);
    }
}
