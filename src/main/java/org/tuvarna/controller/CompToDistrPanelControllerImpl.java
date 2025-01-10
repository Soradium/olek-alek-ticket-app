package org.tuvarna.controller;

import javafx.scene.control.Alert;
import org.tuvarna.command.Command;
import org.tuvarna.entity.Distributor;
import org.tuvarna.entity.Ticket;
import org.tuvarna.entity.Trip;
import org.tuvarna.service.DistributorService;
import org.tuvarna.service.TicketService;
import org.tuvarna.service.TripService;

public class CompToDistrPanelControllerImpl extends RequestPanelController {
    private final TripService tripService;
    private DistributorService distributorService;
    private TicketService ticketService;

    public CompToDistrPanelControllerImpl() {
        this.distributorService = new DistributorService();
        this.tripService = new TripService();
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
            Distributor distributor = (Distributor) requestCommand.getSender();
            Trip tripSentWithCommand = (Trip) requestCommand
                    .getPassedObjects().getFirst();
            tripSentWithCommand.setDistributor(distributor);
            tripService.addTrip(tripSentWithCommand);
            this.distributorService.updateDistributor(distributor);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert!");
            alert.setHeaderText("This distributor now manages the trip.");
            alert.setContentText(distributor.getName() + " is the new manager " +
                    "of trip " + tripSentWithCommand);
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
        super.getCommands().remove(requestCommand);
        super.removeRequest(requestCommand.getMessage());
    }

    public DistributorService getDistributorService() {
        return distributorService;
    }

    public void setDistributorService(DistributorService distributorService) {
        this.distributorService = distributorService;
    }
}
