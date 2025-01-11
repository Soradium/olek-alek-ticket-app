package org.tuvarna.controller;

import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.tuvarna.command.Command;
import org.tuvarna.database.DatabaseSingleton;
import org.tuvarna.entity.*;
import org.tuvarna.service.CashierService;
import org.tuvarna.service.TicketService;

import java.util.ArrayList;
import java.util.List;

public class CashToUsrPanelController extends RequestPanelController{

    private final TicketService ticketService;
    private final CashierService cashierService;

    public CashToUsrPanelController() {
        this.ticketService = new TicketService();
        this.cashierService = new CashierService();
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    public void handleAccept(Command requestCommand) {
        try {
            requestCommand.getSender();
            User user = (User) requestCommand.getSender();
            Ticket ticketSentWithCommand = (Ticket) requestCommand
                    .getPassedObjects().getFirst();
            ticketSentWithCommand.setUser(user);
            ticketSentWithCommand.setSold(true);
            this.ticketService.updateTicket(ticketSentWithCommand);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert!");
            alert.setHeaderText("This cashier now manages the trip.");
            alert.setContentText(user.getName() + " is the new manager " +
                    "of trip " + ticketSentWithCommand);
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert!");
            alert.setHeaderText("Could not assign trip to distributor.");
            alert.setContentText(e.getMessage());
            throw new RuntimeException(e);
        } finally {
            super.getCommands().remove(requestCommand);
            super.removeRequest(requestCommand.getMessage());
        }

    }

    @Override
    public void handleDecline(Command requestCommand) {

    }
}
