package org.tuvarna.controller;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.tuvarna.command.Command;
import org.tuvarna.command.RequestToCompanyCommandImpl;

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
        // prob will be done with init passed from upper class that will store commands
    }

    public ListView<HBox> getRequestListView() {
        return requestListView;
    }

    public void setRequestListView(ListView<HBox> requestListView) {
        this.requestListView = requestListView;
    }

    public void addRequest(Command command) {
        try {
            Label requestLabel = new Label(command.getMessage());
            requestLabel.setStyle("-fx-font-size: 14px;");

            Button acceptButton = new Button("Accept");
            acceptButton.setOnAction(event -> handleAccept(command.getMessage()));

            Button declineButton = new Button("Decline");
            declineButton.setOnAction(event -> handleDecline(command.getMessage()));

            HBox requestItem = new HBox(10, requestLabel, acceptButton, declineButton);
            requestItem.setAlignment(Pos.CENTER);
            requestItem.setPadding(new Insets(5));

            requestListView.getItems().add(requestItem);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void handleAccept(String requestText) {
        System.out.println("Accepted: " + requestText);
        //here something will be sent to parent controller
        removeRequest(requestText);
    }

    private void handleDecline(String requestText) {
        System.out.println("Declined: " + requestText);
        //here something will be sent to parent controller
        removeRequest(requestText);
    }

    private void removeRequest(String requestText) {
        requestListView.getItems().removeIf(hbox -> {
            Label label = (Label) hbox.getChildren().get(0);
            return label.getText().equals(requestText);
        });
    }
}
