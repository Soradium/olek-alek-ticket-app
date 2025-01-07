package org.tuvarna.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.tuvarna.command.Command;
import org.tuvarna.entity.Bus;
import org.tuvarna.entity.Company;
import org.tuvarna.entity.Trip;

import org.tuvarna.observer.Observer;
import org.tuvarna.observer.Subject;
import org.tuvarna.service.BusService;
import org.tuvarna.service.CompanyService;
import org.tuvarna.service.TripService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CompanyController implements Subject {
    @FXML
    private Parent checkRequests;
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
    @FXML
    private RequestPanelController requestPanelController;

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

    private ObservableList<Command> commands = FXCollections.observableArrayList();

    private Observer observer;

    private Company getCurrentCompany() {
        return companyService.getCompanyByName(tripService.getCurrentCompanyName());
    }

    public void initialize() {
        try {
            FXMLLoader requestPanelLoader = new FXMLLoader(getClass().getResource("/org/tuvarna/olekalekproject/check-requests.fxml"));
            checkRequests = requestPanelLoader.load();
            requestPanelController = requestPanelLoader.getController();
            requestPanelController.reloadRequests();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @FXML
//    public boolean respondToRequest(Command command) {
//        //here it should add it to panel
//         boolean requestPanelController.addRequest(command);
//    }

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

    /*

     * TODO: Company -> Distributor
     *  Select company -> Select trip -> Request trip

     * TODO: Distributor -> Cashier
     *  Select trip -> request N tickets

     * TODO: Cashier -> User
     *  Select Ticket -> request Ticket from Cashier X that owns it

     * */

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
        this.observer.update(getCurrentCompany().getName());
    }
}
