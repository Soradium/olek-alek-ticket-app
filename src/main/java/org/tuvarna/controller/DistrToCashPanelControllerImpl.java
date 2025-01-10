package org.tuvarna.controller;

import javafx.scene.control.Alert;
import org.tuvarna.command.Command;
import org.tuvarna.entity.Cashier;
import org.tuvarna.entity.Ticket;
import org.tuvarna.service.CashierService;
import org.tuvarna.service.TicketService;

public class DistrToCashPanelControllerImpl extends RequestPanelController {
    private final CashierService cashierService;
    private final TicketService ticketService;

    public DistrToCashPanelControllerImpl() {
        this.cashierService = new CashierService();
        this.ticketService = new TicketService();
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    void handleAccept(Command requestCommand) {
        try {
            requestCommand.getSender();
            Cashier cashier = (Cashier) requestCommand.getSender();
            Ticket ticketSentWithCommand = (Ticket) requestCommand
                    .getPassedObjects().getFirst();
            ticketSentWithCommand.setDistributor(cashier);
            ticketService.addTicket(ticketSentWithCommand);
            this.cashierService.updateCashier(cashier);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert!");
            alert.setHeaderText("This cashier now manages the trip.");
            alert.setContentText(cashier.getName() + " is the new manager " +
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
    void handleDecline(Command requestCommand) {

    }
}
