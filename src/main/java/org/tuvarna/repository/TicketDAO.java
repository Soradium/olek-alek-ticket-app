package org.tuvarna.repository;

import org.tuvarna.entity.Bus;
import org.tuvarna.entity.Ticket;

import java.util.List;

public interface TicketDAO {
    Ticket getTicketById(int id);
    List<Ticket> getTickets();
    Ticket addTicket(Ticket Ticket);
}
