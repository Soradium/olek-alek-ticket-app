package org.tuvarna.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.entity.Ticket;

import java.util.List;

public class TicketDAOImpl implements TicketDAO {
    private SessionFactory sessionFactory;

    @Override
    public Ticket getTicketById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        Ticket ticket = session.get(Ticket.class, id);
        session.getTransaction().commit();
        return ticket;
    }

    @Override
    public List<Ticket> getTickets() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        List<Ticket> Tickets = session.createQuery("from Ticket").getResultList();
        session.getTransaction().commit();
        session.close();
        return Tickets;
    }

    @Override
    public void addTicket(Ticket Ticket) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.persist(Ticket);
        session.getTransaction().commit();
        session.close();
    }
}
