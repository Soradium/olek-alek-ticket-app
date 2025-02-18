package org.tuvarna.command;

import java.util.List;

public abstract class CommandAbstract implements Command {

    private final String message;

    private List<Object> passedObjects;

    private Object receiver;

    private final Object sender;

    public CommandAbstract(String message, List<Object> passedObjects, Object receiver, Object sender) {
        this.message = message;
        this.passedObjects = passedObjects;
        this.receiver = receiver;
        this.sender = sender;
    }

    @Override
    public List<Object> getPassedObjects() {
        return passedObjects;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Object getReceiver() {
        return receiver;
    }

    @Override
    public void setReceiver(Object receiver) {
        this.receiver = receiver;
    }

    @Override
    public Object getSender() {
        return sender;
    }
}
