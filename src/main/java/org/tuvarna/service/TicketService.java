package org.tuvarna.service;

import org.tuvarna.entity.Ticket;
import org.tuvarna.repository.TicketDAO;

import java.util.List;

public class TicketService {

    private final TicketDAO ticketDAO;

    public TicketService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public List<Ticket> getAllTickets() {
        return ticketDAO.getTickets();
    }

    public Ticket getTicketById(int id) {
        return ticketDAO.getTicketById(id);
    }

    public Ticket addTicket(Ticket ticket) {
        return ticketDAO.addTicket(ticket);
    }
}
