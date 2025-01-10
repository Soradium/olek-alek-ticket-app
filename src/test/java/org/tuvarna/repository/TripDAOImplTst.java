package org.tuvarna.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.tuvarna.entity.Trip;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TripDAOImplTst {
    @Mock
    private SessionFactory sessionFactory;

    @Mock
    private Session session;

    private TableDAO<Trip> tripDAO;

    @Mock
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        when(sessionFactory.getCurrentSession()).thenReturn(session);

        when(session.getTransaction()).thenReturn(transaction);

        tripDAO = new TripDAOImpl(sessionFactory);
    }

    @Test
    void testTripSave() {
        Trip trip = new Trip();
        tripDAO.save(trip);
        verify(session).merge(trip);
    }

    @Test
    void testTripUpdate() {
        Trip trip = new Trip();
        tripDAO.update(trip);
        verify(session).merge(trip);
    }

    @Test
    void testTripFindById() {
        int id = 5;
        Trip mockTrip = new Trip();
        mockTrip.setId(id);
        when(session.get(Trip.class, id)).thenReturn(mockTrip);

        Trip trip = tripDAO.findById(id);

        verify(session).get(Trip.class, id);

        assertNotNull(trip);
        assertEquals(id, trip.getId());
    }

    @Test
    void testTripFindAll() {
        List<Trip> mockList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mockList.add(new Trip());
        }
        Query<Trip> mockQuery = mock(Query.class);
        when(session.createQuery("from Trip", Trip.class)).thenReturn(mockQuery);
        when(mockQuery.getResultList()).thenReturn(mockList);

        List<Trip> trips = tripDAO.findAll();

        verify(session).createQuery("from Trip", Trip.class);
        verify(mockQuery).getResultList();

        assertNotNull(trips);
        assertEquals(mockList.size(), trips.size());
    }


}
