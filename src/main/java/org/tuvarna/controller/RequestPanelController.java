package org.tuvarna.controller;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.tuvarna.command.Command;

import java.util.ArrayList;
import java.util.List;

public abstract class RequestPanelController {

    @FXML
    private ListView<HBox> requestListView;

    @FXML
    public void initialize() {
        reloadRequests();
    }

    private List<Command> commands = new ArrayList<>();

    @FXML
    public void reloadRequests() {
        requestListView.getItems().clear();

        for(Command command : commands) {
            Label requestLabel = new Label(command.getMessage());
            requestLabel.setStyle("-fx-font-size: 14px;");

            Button acceptButton = new Button("Accept");
            acceptButton.setOnAction(event -> handleAccept(command));

            Button declineButton = new Button("Decline");
            declineButton.setOnAction(event -> handleDecline(command));

            HBox requestItem = new HBox(10, requestLabel, acceptButton, declineButton);
            requestItem.setAlignment(Pos.CENTER);
            requestItem.setPadding(new Insets(5));

            requestListView.getItems().add(requestItem);
        }
    }

    public ListView<HBox> getRequestListView() {
        return requestListView;
    }

    public void setRequestListView(ListView<HBox> requestListView) {
        this.requestListView = requestListView;
    }

    public void addCommand(Command command) {
        try {
            commands.add(command);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    abstract void handleAccept(Command requestCommand);
    //System.out.println("Accepted: " + requestText);
    //        //here something will be sent to parent controller
    //

    abstract void handleDecline(Command requestCommand);
    //System.out.println("Declined: " + requestText);
    //        //here something will be sent to parent controller
    //        removeRequest(requestText);

    protected void removeRequest(String requestText) {
        requestListView.getItems().removeIf(hbox -> {
            Label label = (Label) hbox.getChildren().get(0);
            return label.getText().equals(requestText);
        });
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }
}
