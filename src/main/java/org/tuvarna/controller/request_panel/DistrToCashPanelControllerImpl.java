package org.tuvarna.controller.request_panel;

import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.command.Command;
import org.tuvarna.controller.DistributorController;
import org.tuvarna.database.DatabaseSingleton;
import org.tuvarna.entity.Cashier;
import org.tuvarna.entity.Seat;
import org.tuvarna.entity.Ticket;
import org.tuvarna.entity.Trip;
import org.tuvarna.service.CashierService;
import org.tuvarna.service.TicketService;
import org.tuvarna.service.TripService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DistrToCashPanelControllerImpl extends RequestPanelController {

    private static final Logger logger = LogManager.getLogger(DistrToCashPanelControllerImpl.class);
    private final CashierService cashierService;
    private final TicketService ticketService;
    private final TripService tripService;
    SessionFactory sessionFactory = DatabaseSingleton.getInstance().getSessionFactory();
    private DistributorController distributorController;

    public DistrToCashPanelControllerImpl() {
        this.cashierService = new CashierService();
        this.ticketService = new TicketService();
        this.tripService = new TripService();

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
            logger.info("Sender: {}", cashier.toString());
            Trip tripSentWithCommand = (Trip) requestCommand
                    .getPassedObjects().getFirst();
            logger.info("Trip sent: {}", tripSentWithCommand);
            List<Seat> tempList = new ArrayList<>();
            tripSentWithCommand.setCashier(cashier);
            tripService.addTrip(tripSentWithCommand);
            this.cashierService.updateCashier(cashier);
            logger.info("Cashier updated: {}", cashier);
            logger.info("Tickets creation");
            for (int i = 0; i < 20; i++) {
                Ticket currentTicket = new Ticket();
                tempList.add(seatCreation(tripSentWithCommand, (i + 1)));
                currentTicket.setSeat(tempList.get(i));
                currentTicket.setDistributor(cashier);
                currentTicket.setTrip(tripSentWithCommand);
                ticketService.addTicket(currentTicket);
                logger.info("Ticket created: {}", currentTicket);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert!");
            alert.setHeaderText("This cashier now manages the trip.");
            alert.setContentText(cashier.getName() + " is the new manager " +
                    "of trip " + tripSentWithCommand);
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

    private Seat seatCreation(Trip trip, int seatNumber) {
        logger.info("Session receiving");
        Session session = sessionFactory.getCurrentSession();
        logger.info("Begin transaction");
        session.beginTransaction();
        try {
            Seat seat = session.merge(new Seat(trip.getBus(), seatNumber));
            session.getTransaction().commit();
            return seat;
        } catch (Exception e) {
            logger.error("Error during transaction with message {}", e.getMessage());
        }
        return null;
    }

    @Override
    void handleDecline(Command requestCommand) {
        super.getCommands().remove(requestCommand);
        super.removeRequest(requestCommand.getMessage());
        logger.info("Requests removed");
    }

    @Override
    protected List<Command> getParticularCommands() {
        List<Command> particularCommands = new LinkedList<>();
        String distributorName = distributorController.distributorName.getText();
        super.getCommands().stream()
                .filter(c ->
                        (c.getReceiver() instanceof DistributorController) &&
                                (((Trip) c.getPassedObjects().getFirst())
                                        .getDistributor().getName()
                                        .equals(distributorName))
                )
                .forEach(c -> particularCommands.add(c));

        return particularCommands;
    }

    public DistributorController getDistributorController() {
        return distributorController;
    }

    public void setDistributorController(DistributorController distributorController) {
        this.distributorController = distributorController;
    }
}
