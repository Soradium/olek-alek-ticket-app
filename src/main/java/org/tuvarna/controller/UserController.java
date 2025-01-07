package org.tuvarna.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleGroup;

public class UserController {
    @FXML
    private ToggleGroup ratingGroup;
    private String currentUser;

    public void orderTicket(ActionEvent actionEvent) {
    }

    public void submitRating(ActionEvent actionEvent) {

    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }
}
