package org.tuvarna.command;

import org.tuvarna.controller.CompanyController;

import java.util.List;

public class RequestToCompanyCommandImpl extends CommandAbstract {
    public RequestToCompanyCommandImpl(String message, List<Object> passedObjects, Object receiver, Object sender) {
        super(message, passedObjects, receiver, sender);
    }

    @Override
    public void execute() {
        CompanyController companyController = (CompanyController) this.getReceiver();
        companyController.respondToRequest(this);
    }
}
