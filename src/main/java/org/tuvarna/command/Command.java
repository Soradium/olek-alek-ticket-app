package org.tuvarna.command;

import java.util.List;

public interface Command {
     List<Object> getPassedObjects();
     String getMessage();
     boolean getApprovalState();
     void setApprovalState(boolean approvalState);
     Object getReceiver();
     void setReceiver(Object receiver);
     void execute();
}

//TODO: Всё должно проходить через контроллер Компании как медиатора.
// Контроллер дистрибутора запрашивает конкретный трип, этот трип
//

