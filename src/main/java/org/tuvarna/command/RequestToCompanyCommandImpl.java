package org.tuvarna.command;

import org.tuvarna.controller.CompanyController;
import org.tuvarna.entity.Company;
import org.tuvarna.entity.Distributor;

import java.util.List;

public class RequestToCompanyCommandImpl extends CommandAbstract {
    private Distributor distributor;
    public RequestToCompanyCommandImpl(String message,
                                       List<Object> passedObjects,
                                       boolean approvalState,
                                       Object receiver,
                                       Distributor distributor) {
        super(message, passedObjects, approvalState, receiver);
        this.distributor = distributor;
    }

    //command = new RequestToCompanyCommandImpl(
    //                    message,
    //                    selectedTripList,
    //                    false,
    //                    companyController);
    @Override
    public void execute() {
        CompanyController companyController = (CompanyController) this.getReceiver();
        companyController.respondToRequest(this);
    }
}
