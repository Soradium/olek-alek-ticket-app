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
        reloadRequests(); // Load initial data when the application starts
    }

    @FXML
    public void reloadRequests() {
        // Clear existing requests
        requestListView.getItems().clear();

        // Load new requests (replace with actual data fetching logic)
        for (int i = 1; i <= 10; i++) {
            addRequest("Request #" + i);
        }
    }

    private void addRequest(String requestText) {
        // Create label for request
        Label requestLabel = new Label(requestText);
        requestLabel.setStyle("-fx-font-size: 14px;");

        // Create Accept button
        Button acceptButton = new Button("Accept");
        acceptButton.setOnAction(event -> handleAccept(requestText));

        // Create Decline button
        Button declineButton = new Button("Decline");
        declineButton.setOnAction(event -> handleDecline(requestText));

        // Create HBox to hold label and buttons
        HBox requestItem = new HBox(10, requestLabel, acceptButton, declineButton);
        requestItem.setAlignment(Pos.CENTER);
        requestItem.setPadding(new Insets(5));

        // Add HBox to ListView
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
        // Remove the specific HBox from the ListView
        requestListView.getItems().removeIf(hbox -> {
            Label label = (Label) hbox.getChildren().get(0); // First child is the label
            return label.getText().equals(requestText);
        });
    }
}
