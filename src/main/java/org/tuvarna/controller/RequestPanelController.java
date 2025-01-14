package org.tuvarna.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tuvarna.command.Command;

import java.util.ArrayList;
import java.util.List;

public abstract class RequestPanelController {

    @FXML
    private ListView<HBox> requestListView;

    private List<Command> commands = new ArrayList<>();

    private final static Logger logger = LogManager.getLogger(RequestPanelController.class);

    @FXML
    public void initialize() {
        reloadRequests();
    }

    @FXML
    public void reloadRequests() {
        requestListView.getItems().clear();
        logger.info("RequestListView cleared");
        for (Command command : commands) {
            Label requestLabel = new Label(command.getMessage());
            requestLabel.setStyle("-fx-font-size: 14px;");
            requestLabel.setWrapText(true);
            requestLabel.setMaxWidth(400);

            Button acceptButton = new Button("Accept");
            acceptButton.setOnAction(event -> handleAccept(command));

            Button declineButton = new Button("Decline");
            declineButton.setOnAction(event -> handleDecline(command));

            HBox requestItem = new HBox(10, requestLabel, acceptButton, declineButton);
            requestItem.setAlignment(Pos.CENTER);
            requestItem.setPadding(new Insets(5));

            requestItem.setOnMouseClicked(event -> {
                if (requestItem.getStyle().contains("selected")) {
                    requestItem.setStyle("-fx-background-color: #cbcbcb; -fx-wrap-text: true; -fx-text-fill: black;");
                } else {
                    requestItem.setStyle("-fx-background-color: #f4f4f4; -fx-text-fill: black;");
                }
            });
            logger.info("Configured styles for RequestListView");

            requestListView.getItems().add(requestItem);
            logger.info("All requests added");
        }
    }

    public void addCommand(Command command) {
        try {
            commands.add(command);
            logger.info("Successfully added command: {}", command.getMessage());
        } catch (Exception e) {
            logger.error("Occurred error in addCommand method, with message: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    abstract void handleAccept(Command requestCommand);

    abstract void handleDecline(Command requestCommand);

    protected void removeRequest(String requestText) {
        requestListView.getItems().removeIf(hbox -> {
            Label label = (Label) hbox.getChildren().get(0);
            return label.getText().equals(requestText);
        });
        logger.info("All requests successfully removed");
    }

    public List<Command> getCommands() {
        return commands;
    }
}
