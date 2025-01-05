package org.tuvarna.controller;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class RequestPanelController {

    @FXML
    private ListView<HBox> requestListView;

    @FXML
    public void initialize() {
        reloadRequests();
    }

    @FXML
    public void reloadRequests() {
        requestListView.getItems().clear();

        for (int i = 1; i <= 10; i++) {
            addRequest("Request #" + i);
        }
    }

    private void addRequest(String requestText) {
        Label requestLabel = new Label(requestText);
        requestLabel.setStyle("-fx-font-size: 14px;");

        Button acceptButton = new Button("Accept");
        acceptButton.setOnAction(event -> handleAccept(requestText));

        Button declineButton = new Button("Decline");
        declineButton.setOnAction(event -> handleDecline(requestText));

        HBox requestItem = new HBox(10, requestLabel, acceptButton, declineButton);
        requestItem.setAlignment(Pos.CENTER);
        requestItem.setPadding(new Insets(5));

        requestListView.getItems().add(requestItem);
    }

    private void handleAccept(String requestText) {
        System.out.println("Accepted: " + requestText);
        removeRequest(requestText);
    }

    private void handleDecline(String requestText) {
        System.out.println("Declined: " + requestText);
        removeRequest(requestText);
    }

    private void removeRequest(String requestText) {
        requestListView.getItems().removeIf(hbox -> {
            Label label = (Label) hbox.getChildren().get(0); // First child is the label
            return label.getText().equals(requestText);
        });
    }
}
