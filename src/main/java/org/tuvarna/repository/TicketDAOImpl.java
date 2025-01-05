package org.tuvarna.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.entity.Ticket;

import java.util.List;

public class TicketDAOImpl implements TicketDAO {
    private final SessionFactory sessionFactory;

    public TicketDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Ticket getTicketById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            Ticket ticket = session.get(Ticket.class, id);
            session.getTransaction().commit();
            return ticket;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public List<Ticket> getTickets() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            List<Ticket> tickets = session.createQuery("from Ticket", Ticket.class).getResultList();
            session.getTransaction().commit();
            return tickets;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }

    @Override
    public Ticket addTicket(Ticket ticket) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        try {
            session.persist(ticket);
            session.getTransaction().commit();
            return ticket;
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return null;
    }
}
