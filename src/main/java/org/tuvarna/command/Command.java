package org.tuvarna.command;

import java.util.List;

public interface Command {
     List<Object> getPassedObjects();
     String getMessage();
     Object getReceiver();
     void setReceiver(Object receiver);
     void execute();
     Object getSender();
}
