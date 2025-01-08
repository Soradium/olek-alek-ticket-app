package org.tuvarna.controller;

import org.tuvarna.command.Command;
import org.tuvarna.entity.Distributor;
import org.tuvarna.entity.Trip;
import org.tuvarna.factories.FactoryDAO;
import org.tuvarna.service.DistributorService;
import org.tuvarna.service.TripService;

import java.util.List;

public class CompToDistrPanelControllerImpl extends RequestPanelController{
    private DistributorService distributorService;

    private TripService tripService;

    public CompToDistrPanelControllerImpl() {
        this.distributorService = new DistributorService();
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
            Distributor distributor = (Distributor) requestCommand.getSender();
            Trip tripSentWithCommand = (Trip) requestCommand
                    .getPassedObjects().getFirst();
            tripSentWithCommand.setDistributor(distributor);
            tripService.addTrip(tripSentWithCommand);
            this.distributorService.updateDistributor(distributor);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            super.removeRequest(requestCommand.getMessage());
        }
    }

    @Override
    void handleDecline(Command requestCommand) {
        super.removeRequest(requestCommand.getMessage());
    }

    public DistributorService getDistributorService() {
        return distributorService;
    }

    public void setDistributorService(DistributorService distributorService) {
        this.distributorService = distributorService;
    }
}
