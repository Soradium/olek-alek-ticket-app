package org.tuvarna.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.entity.*;

import java.util.List;

public class TicketDAOImpl implements TableDAO<Ticket> {

    private final SessionFactory sessionFactory;

    public TicketDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    private final static Logger logger = LogManager.getLogger(TicketDAOImpl.class);

    @Override
    public Ticket findById(int id) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for findById");
        try {
            Ticket ticket = session.get(Ticket.class, id);
            logger.info("Ticket with id {} fetched successfully", id);
            session.getTransaction().commit();
            logger.info("Transaction committed successfully");
            return ticket;
        } catch (Exception e) {
            logger.error("Occurred with error in findByAll method, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rollback successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }

    @Override
    public List<Ticket> findAll() {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for findAll");
        try {
            List<Ticket> tickets = session.createQuery("from Ticket", Ticket.class).getResultList();
            logger.info("Tickets fetched successfully: {}", tickets.size());
            session.getTransaction().commit();
            logger.info("Transaction committed successfully");
            return tickets;
        } catch (Exception e) {
            logger.error("Occurred with error in fidnByAll method, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rollback successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }

    @Override
    public Ticket save(Ticket ticket) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for save");
        try {
            session.merge(ticket);
            logger.info("Ticket {} saved successfully", ticket);
            session.getTransaction().commit();
            logger.info("Transaction committed successfully");
            return ticket;
        } catch (Exception e) {
            logger.error("Occurred with error in fidnByAll method, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rollback successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }

    @Override
    public Ticket update(Ticket ticket) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Session started for update");
        try {
            session.merge(ticket);
            logger.info("Ticket {} updated successfully", ticket);
            session.getTransaction().commit();
            logger.info("Transaction committed successfully");
            return ticket;
        } catch (Exception e) {
            logger.error("Occurred with error in fidnByAll method, with message: {}", e.getMessage());
            session.getTransaction().rollback();
            logger.info("Transaction rollback successfully");
        } finally {
            session.close();
            logger.info("Session closed successfully");
        }
        return null;
    }
}
