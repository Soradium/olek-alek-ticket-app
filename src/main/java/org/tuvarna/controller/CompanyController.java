package org.tuvarna.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.tuvarna.entity.Bus;
import org.tuvarna.entity.Company;
import org.tuvarna.entity.Trip;

import org.tuvarna.service.BusService;
import org.tuvarna.service.CompanyService;
import org.tuvarna.service.TripService;

import java.time.LocalDate;


public class CompanyController  {
    @FXML
    private TextField busNumber;
    @FXML
    private Label ratingLabel;
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

    private TripService tripService;

    private CompanyService companyService;

    private BusService busService;

    public void setBusService(BusService busService) {
        this.busService = busService;
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void setTripService(TripService tripService) {
        this.tripService = tripService;
    }

    private ObservableList<Trip> trips = FXCollections.observableArrayList();

    private Company getCurrentCompany() {
        return companyService.getCompanyByName(tripService.getCurrentCompanyName());
    }

    @FXML
    public void addTrip(){
        String departureText = departure.getText();
        String destinationText = destination.getText();
        LocalDate timeOfDepartureText = LocalDate.parse(timeOfDeparture.getText());
        String tripTypeText = tripType.getText();
        System.out.println("Local date: " + timeOfDepartureText);
        trips.add(tripService.addTrip(new Trip(departureText,
                destinationText,
                timeOfDepartureText,
                tripTypeText,
                getCurrentCompany()))
        );
        departure.clear();
        destination.clear();
        timeOfDeparture.clear();
        tripType.clear();
    }
    @FXML
    public void addBus(){
        busService.addBus(new Bus(getCurrentCompany()));
    }


    @FXML
    private void showInfo() {
        tripListView.getItems().clear();
        trips.addAll(tripService.getAllTripsByCompany());
        ratingLabel.setText(String.valueOf(getCurrentCompany().getCurrentRating()));
        tripListView.setItems(trips);
    }

}
