package org.tuvarna.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.tuvarna.command.Command;
import org.tuvarna.command.RequestToDistributorCommandImpl;
import org.tuvarna.entity.Cashier;
import org.tuvarna.entity.Ticket;
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
    public ListView<Ticket> ticketsProvidedByCashier;

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

    private static final Logger logger = LogManager.getLogger(CashierController.class);

    @FXML
    public void initialize() {
        try {
            logger.info("Try to add requestPanel for {}", CashierController.class.getSimpleName());
            FXMLLoader requestPanelLoader = new FXMLLoader(getClass().getResource("/org/tuvarna/olekalekproject/check-requests-cashier.fxml"));
            checkRequests = requestPanelLoader.load();
            requestPanelController = requestPanelLoader.<CashToUsrPanelController>getController();
            logger.info("Successfully loaded check-requests-cashier.fxml");

        } catch (Exception e) {
            logger.error("Error during initialize of check-requests-cashier. Error: {}", e.getMessage());
        }
    }

    @FXML
    public void respondToRequest(Command command) {
        requestPanelController.addCommand(command);
        logger.info("Successfully respond to request");
    }

    @FXML
    public void requestTrip() {
        Trip selectedTrip = tripsByDistributorListView.getSelectionModel().getSelectedItem();
        logger.info("Selected trip: {}", selectedTrip);
        if (selectedTrip != null) {
            logger.info("Checking the existence of a cashier");
            if (selectedTrip.getCashier() != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert!");
                alert.setHeaderText("Trip is managed!");
                alert.setContentText("This trip is already managed by another" +
                        " cashier.");
                alert.showAndWait();
                logger.info("Trip is managed by another cashier");
                return;
            }
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
            logger.info("Successfully accepted a trip request");
            command.execute();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success!");
            alert.setHeaderText("Trip was requested.");
            alert.setContentText("Request was sent to corresponding distributor.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert!");
            alert.setHeaderText("Trip was not chosen!");
            alert.setContentText("Request was not sent.");
            alert.showAndWait();
        }
    }

    public void showInfo() {
        logger.info("Clearing of tripsByDistributorListView");
        tripsByDistributorListView.getItems().clear();
        List<Trip> allTrips = tripService.getAllTrips();
        logger.info("All trips: {}", allTrips.size());
        logger.info("Filtering of allTrips by distributor id: {}", currentCashier.getDistributor().getId());
        ObservableList<Trip> distributorsTrips = FXCollections.
                observableArrayList(
                        allTrips.stream().filter(c -> c.getDistributor() != null && c.getDistributor().getId() == currentCashier.getDistributor().getId())
                                .toList());
        logger.info("Filtered allTrips list: {}", distributorsTrips.size());
        tripsByDistributorListView.setItems(distributorsTrips);
        logger.info("Trips in list view {}", tripsByDistributorListView.getItems().size());
        tripsByDistributorListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Trip item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("-fx-background-color:  #f4f4f4; -fx-font-size: 14px;");
                } else {
                    Label label = new Label(item.toString());
                    label.setWrapText(true);
                    label.setStyle("-fx-font-size: 14px;");

                    label.setMaxWidth(tripsByDistributorListView.getWidth());
                    if(isSelected()){
                        label.setStyle("-fx-background-color:  #cbcbcb;-fx-font-size: 14px; -fx-text-fill: black;");
                    }else{
                        label.setStyle("-fx-background-color: #f4f4f4; -fx-font-size: 14px;");

                    }
                    setGraphic(label);
                }
            }
        });
        logger.info("Configured customCellFactory for tripsByDistributorLostView");
    }

    public void setCurrentCashier(Cashier currentCashier) {
        this.currentCashier = currentCashier;
    }

    public CashierService getService() {
        return service;
    }

    public void setService(CashierService service) {
        this.service = service;
    }

    public void setTripService(TripService tripService) {
        this.tripService = tripService;
    }

    public void setDistributorController(DistributorController distributorController) {
        this.distributorController = distributorController;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName.setText(distributorName);
    }

    public void setCashierName(String cashierName) {
        this.cashierName.setText(cashierName);
    }

    public Parent getCheckRequests() {
        return checkRequests;
    }

    @Override
    public void registerObserver(Observer observer) {
        this.observer = observer;
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
}
