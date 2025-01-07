package org.tuvarna.command;

import java.util.List;

public abstract class CommandAbstract implements Command {
    private String message;

    private List<Object> passedObjects;

    private boolean approvalState;

    private Object receiver;

    public CommandAbstract(String message, List<Object> passedObjects, boolean approvalState, Object receiver) {
        this.message = message;
        this.passedObjects = passedObjects;
        this.approvalState = approvalState;
        this.receiver = receiver;
    }
    @Override
    public List<Object> getPassedObjects() {
        return passedObjects;
    }
    public void setPassedObjects(List<Object> passedObjects) {
        this.passedObjects = passedObjects;
    }
    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public boolean getApprovalState() {
        return approvalState;
    }

    public void setApprovalState(boolean approvalState) {
        this.approvalState = approvalState;
    }
    @Override
    public Object getReceiver() {
        return receiver;
    }

    public void setReceiver(Object receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {

    }
}
