package org.tuvarna.command;

import org.tuvarna.controller.CashierController;

import java.util.List;

public class RequestToCashierCommandImpl extends CommandAbstract{

    public RequestToCashierCommandImpl(String message, List<Object> passedObjects, Object receiver, Object sender) {
        super(message, passedObjects, receiver, sender);
    }
    @Override
    public void execute() {
        CashierController cashierController = (CashierController) this.getReceiver();
        cashierController.respondToRequest(this);
    }
}
