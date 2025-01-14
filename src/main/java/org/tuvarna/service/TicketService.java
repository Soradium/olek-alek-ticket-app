package org.tuvarna.service;

import org.tuvarna.entity.Ticket;
import org.tuvarna.factories.FactoryDAO;
import org.tuvarna.repository.TableDAO;

import java.util.List;

public class TicketService {

    private final TableDAO<Ticket> ticketDAO;

    public TicketService() {
        this.ticketDAO = FactoryDAO.getInstance().getDao(Ticket.class);
    }

    public List<Ticket> getAllTickets() {
        return ticketDAO.findAll();
    }

    public Ticket getTicketById(int id) {
        return ticketDAO.findById(id);
    }

    public Ticket addTicket(Ticket ticket) {
        return ticketDAO.save(ticket);
    }

    public Ticket updateTicket(Ticket ticket) {
        return ticketDAO.update(ticket);
    }
}
