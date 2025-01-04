package org.tuvarna.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.tuvarna.entity.Trip;
import org.tuvarna.service.TripService;

import java.util.List;


public class CompanyController {
    @FXML
    private ListView<Trip> tripListView;
    @FXML
    private TextField departure;
    @FXML
    private TextField destination;
    @FXML
    private TextField timeOfDeparture;
    @FXML
    private TextField tripType;

    private TripService tripService = new TripService();
    private ObservableList<Trip> trips = FXCollections.observableArrayList();

    @FXML
    public void addTrip(){
        String departureText = departure.getText();
        String destinationText = destination.getText();
        String timeOfDepartureText = timeOfDeparture.getText();
        String tripTypeText = tripType.getText();
        trips.add(tripService.addTrip(new Trip(destinationText, departureText, timeOfDepartureText, tripTypeText)));
        departure.clear();
        destination.clear();
        timeOfDeparture.clear();
        tripType.clear();
    }

    public void addBus(){

    }
    @FXML
    private void showInfo() {
        tripListView.getItems().clear();
        trips.addAll(tripService.getAllTrips());
        tripListView.setItems(trips);
    }
}
