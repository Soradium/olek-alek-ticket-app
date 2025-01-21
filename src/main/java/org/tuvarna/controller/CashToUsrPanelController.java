package org.tuvarna.controller;

import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tuvarna.command.Command;
import org.tuvarna.entity.Ticket;
import org.tuvarna.entity.User;
import org.tuvarna.service.TicketService;

import java.util.LinkedList;
import java.util.List;

public class CashToUsrPanelController extends RequestPanelController {

    private static final Logger logger = LogManager.getLogger(CashToUsrPanelController.class);
    private final TicketService ticketService;
    private CashierController cashierController;

    public CashToUsrPanelController() {
        this.ticketService = new TicketService();
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
            logger.info("Sender: {}", user.toString());
            Ticket ticketSentWithCommand = (Ticket) requestCommand
                    .getPassedObjects().getFirst();
            logger.info("Ticket sent: {}", ticketSentWithCommand);
            ticketSentWithCommand.setUser(user);
            ticketSentWithCommand.setSold(true);
            this.ticketService.updateTicket(ticketSentWithCommand);
            logger.info("Ticket updated: {}", ticketSentWithCommand);
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
            logger.error("Error during handleAccept function, with message {}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            super.getCommands().remove(requestCommand);
            super.removeRequest(requestCommand.getMessage());
            logger.info("Requests removed");
        }

    }

    @Override
    public void handleDecline(Command requestCommand) {
        super.getCommands().remove(requestCommand);
        super.removeRequest(requestCommand.getMessage());
        logger.info("Requests removed");
    }

    @Override
    protected List<Command> getParticularCommands() {
        List<Command> particularCommands = new LinkedList<>();
        String cashierName = cashierController.getCashierName().getText();
        super.getCommands().stream()
                .filter(c ->
                        (c.getReceiver() instanceof CashierController) &&
                                (((CashierController) c.getReceiver())
                                        .getCashierName()
                                        .getText()
                                        .equals(cashierName))
                )
                .forEach(c -> particularCommands.add(c));

        return particularCommands;
    }

    public CashierController getCashierController() {
        return cashierController;
    }

    public void setCashierController(CashierController cashierController) {
        this.cashierController = cashierController;
    }
}
