package org.tuvarna.command;

import org.tuvarna.controller.CompanyController;
import org.tuvarna.controller.DistributorController;
import org.tuvarna.entity.Distributor;

import java.util.List;

public class RequestToDistributorCommandImpl extends CommandAbstract{

    public RequestToDistributorCommandImpl(String message, List<Object> passedObjects, Object receiver, Object sender) {
        super(message, passedObjects, receiver, sender);
    }
    @Override
    public void execute() {
        DistributorController distributorController = (DistributorController) this.getReceiver();
        distributorController.respondToRequest(this);
    }
}
