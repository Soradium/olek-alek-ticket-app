package org.tuvarna.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.tuvarna.command.Command;
import org.tuvarna.command.RequestToDistributorCommandImpl;
import org.tuvarna.entity.Cashier;
import org.tuvarna.entity.Trip;
import org.tuvarna.observer.Observer;
import org.tuvarna.observer.Subject;
import org.tuvarna.service.CashierService;
import org.tuvarna.service.TripService;

import java.util.ArrayList;
import java.util.List;

public class CashierController implements Subject {
    @FXML
    public ListView<Trip> tripsByDistributorListView;
    @FXML
    private Label cashierName;
    @FXML
    private Label distributorName;
    @FXML
    private RequestPanelController requestPanelController;
    @FXML
    private Parent checkRequests;

    private TripService tripService;

    private Command command;

    private DistributorController distributorController;

    private CashierService service;
    private Cashier currentCashier;

    private Observer observer;

    @FXML
    public void initialize() {
        try {
            FXMLLoader requestPanelLoader = new FXMLLoader(getClass().getResource("/org/tuvarna/olekalekproject/check-requests-cashier.fxml"));
            checkRequests = requestPanelLoader.load();
            requestPanelController = requestPanelLoader.<CashToUsrPanelController>getController();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void respondToRequest(Command command) {
        requestPanelController.addCommand(command);
    }

    @FXML
    public void requestTrip() {
        Trip selectedTrip = tripsByDistributorListView.getSelectionModel().getSelectedItem();
        if (selectedTrip != null) {
//            if (selectedTrip.getDistributor().getCashiers() != null) {
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Alert!");
//                alert.setHeaderText("Trip is managed!");
//                alert.setContentText("This trip is already managed by another" +
//                        " cashier.");
//                alert.showAndWait();
//                return;
//            }
            String message = "Would you like to accept a trip request to "
                    + currentCashier.getName() + ": " + selectedTrip + " ?";
            List<Object> selectedTripList = new ArrayList<>();
            selectedTripList.add(selectedTrip);
            command = new RequestToDistributorCommandImpl(
                    message,
                    selectedTripList,
                    distributorController,
                    service.getCashierByName(currentCashier.getName())
            );
            command.execute();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText("Trip was requested.");
            alert.setContentText("Request was sent to corresponding company.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert!");
            alert.setHeaderText("Trip was not chosen!");
            alert.setContentText("Request was not sent.");
            alert.showAndWait();
        }
    }

    private void loadCashierDetails(String name) {

        currentCashier = service.getCashierByName(name);
        if (currentCashier != null) {
            cashierName.setText(currentCashier.getName());
        } else {
            cashierName.setText("Cashier not found");
        }
    }

    public void setCashierService(CashierService service) {
        this.service = service;
    }

    public Cashier getCurrentCashier() {
        return currentCashier;
    }

    public void setCurrentCashier(Cashier currentCashier) {
        this.currentCashier = currentCashier;
    }

    public void showInfo() {
        tripsByDistributorListView.getItems().clear();
        List<Trip> allTrips = tripService.getAllTrips();
        ObservableList<Trip> distributorsTrips = FXCollections.
                observableArrayList(
                        allTrips.stream().filter(c -> c.getDistributor() != null && c.getDistributor().getId() == currentCashier.getDistributor().getId())
                                .toList());

        tripsByDistributorListView.setItems(distributorsTrips);

    }

    public CashierService getService() {
        return service;
    }

    public void setService(CashierService service) {
        this.service = service;
    }

    public TripService getTripService() {
        return tripService;
    }

    public void setTripService(TripService tripService) {
        this.tripService = tripService;
    }

    public void setDistributorController(DistributorController distributorController) {
        this.distributorController = distributorController;
    }

    public void setRequestPanelController(RequestPanelController requestPanelController) {
        this.requestPanelController = requestPanelController;
    }

    @Override
    public void registerObserver(Observer observer) {
        this.observer = observer; //main controller
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observer = null;
    }

    @Override
    public void notifyObservers() {
        List<String> values = new ArrayList<>();
        values.add(cashierName.getText());
        values.add("Cashiers");
        this.observer.update(values);
    }

    public Label getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName.setText(distributorName);
    }
}
