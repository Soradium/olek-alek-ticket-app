package org.tuvarna.controller;

import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tuvarna.command.Command;
import org.tuvarna.entity.Distributor;
import org.tuvarna.entity.Trip;
import org.tuvarna.service.DistributorService;
import org.tuvarna.service.TripService;

public class CompToDistrPanelControllerImpl extends RequestPanelController {

    private final TripService tripService;

    private DistributorService distributorService;

    public final static Logger logger = LogManager.getLogger(CompToDistrPanelControllerImpl.class.getName());

    public CompToDistrPanelControllerImpl() {
        this.distributorService = new DistributorService();
        this.tripService = new TripService();
    }

    @Override
    public void initialize() {
        super.initialize();}

    @Override
    void handleAccept(Command requestCommand) {
        try {
            requestCommand.getSender();
            Distributor distributor = (Distributor) requestCommand.getSender();
            logger.info("Sender: {}", distributor.toString());
            Trip tripSentWithCommand = (Trip) requestCommand
                    .getPassedObjects().getFirst();
            logger.info("Trip sent: {}", tripSentWithCommand);
            tripSentWithCommand.setDistributor(distributor);
            tripService.addTrip(tripSentWithCommand);
            this.distributorService.updateDistributor(distributor);
            logger.info("Distributor updated: {}", distributor);
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
            logger.error("Error during handleAccept function, with message {}",e.getMessage());
            throw new RuntimeException(e);
        } finally {
            super.getCommands().remove(requestCommand);
            super.removeRequest(requestCommand.getMessage());
            logger.info("Requests removed");
        }
    }

    @Override
    void handleDecline(Command requestCommand) {
        super.getCommands().remove(requestCommand);
        super.removeRequest(requestCommand.getMessage());
        logger.info("Requests removed");
    }
}
