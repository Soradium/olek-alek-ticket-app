package org.tuvarna.controller;

import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tuvarna.command.Command;
import org.tuvarna.entity.Distributor;
import org.tuvarna.entity.Trip;
import org.tuvarna.service.DistributorService;
import org.tuvarna.service.TripService;

import java.util.LinkedList;
import java.util.List;

public class CompToDistrPanelControllerImpl extends RequestPanelController {
    public final static Logger logger = LogManager.getLogger(CompToDistrPanelControllerImpl.class.getName());
    private final TripService tripService;
    private final DistributorService distributorService;
    private CompanyController companyController;

    public CompToDistrPanelControllerImpl() {
        this.distributorService = new DistributorService();
        this.tripService = new TripService();
    }

    @Override
    public void initialize() {
        super.initialize();
    }

    @Override
    protected void handleAccept(Command requestCommand) {
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
            logger.error("Error during handleAccept function, with message {}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            super.getCommands().remove(requestCommand);
            super.removeRequest(requestCommand.getMessage());
            logger.info("Requests removed");
        }
    }

    @Override
    protected void handleDecline(Command requestCommand) {
        super.getCommands().remove(requestCommand);
        super.removeRequest(requestCommand.getMessage());
        logger.info("Requests removed");
    }

    @Override
    protected List<Command> getParticularCommands() {
        List<Command> particularCommands = new LinkedList<>();
        String companyName = companyController.companyName.getText();
        super.getCommands().stream()
                .filter(c ->
                        (c.getReceiver() instanceof CompanyController) &&
                                (((Trip) c.getPassedObjects().getFirst())
                                        .getCompany().getName()
                                        .equals(companyName))
                )
                .forEach(c -> particularCommands.add(c));

        return particularCommands;
    }

    public CompanyController getCompanyController() {
        return companyController;
    }

    public void setCompanyController(CompanyController companyController) {
        this.companyController = companyController;
    }
}
