package org.tuvarna.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.tuvarna.entity.Trip;
import org.tuvarna.service.BusService;
import org.tuvarna.service.TripService;

import java.time.LocalDate;
import java.util.List;


public class CompanyController {
    @FXML
    public TextField busNumber;
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

    private String currentCompanyChosen;

    private TripService tripService;
    private BusService busService;

    public void setTripService(TripService tripService, BusService busService) {
        this.tripService = tripService;
        this.busService = busService;
    }

    private ObservableList<Trip> trips = FXCollections.observableArrayList();

    @FXML
    public void addTrip(){
        String departureText = departure.getText();
        String destinationText = destination.getText();
        LocalDate timeOfDepartureText = LocalDate.parse(timeOfDeparture.getText());
        String tripTypeText = tripType.getText();
        System.out.println("Local date: " + timeOfDepartureText);
        //TODO: Fix trip adding parameters down below
        trips.add(tripService.addTrip(new Trip(departureText, destinationText, timeOfDepartureText, tripTypeText)));
        departure.clear();
        destination.clear();
        timeOfDeparture.clear();
        tripType.clear();
    }
    @FXML
    public void addBus(){
        int busNumberText = Integer.parseInt(busNumber.getText());

    }
    @FXML
    private void showInfo() {
        tripListView.getItems().clear();
        trips.addAll(tripService.getAllTrips());
        tripListView.setItems(trips);
    }

    public void setCurrentCompanyChosen(String currentCompanyChosen) {
        this.currentCompanyChosen = currentCompanyChosen;
    }
}
