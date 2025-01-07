package org.tuvarna.command;

import org.tuvarna.controller.CompanyController;

import java.util.List;

public class RequestToCompanyCommandImpl extends CommandAbstract {
    public RequestToCompanyCommandImpl(String message,
                                       List<Object> passedObjects,
                                       boolean approvalState,
                                       Object receiver) {
        super(message, passedObjects, approvalState, receiver);
    }

    //command = new RequestToCompanyCommandImpl(
    //                    message,
    //                    selectedTripList,
    //                    false,
    //                    companyController);
    @Override
    public void execute() {
//        CompanyController receiver = (CompanyController) this.getReceiver();
//        receiver.addRequest(this);
    }
}
