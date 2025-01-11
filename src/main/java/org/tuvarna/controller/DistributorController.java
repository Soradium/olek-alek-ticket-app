package org.tuvarna.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.tuvarna.command.Command;
import org.tuvarna.command.RequestToCompanyCommandImpl;
import org.tuvarna.entity.Cashier;
import org.tuvarna.entity.Company;
import org.tuvarna.entity.Trip;
import org.tuvarna.observer.Observer;
import org.tuvarna.observer.Subject;
import org.tuvarna.service.*;

import java.util.ArrayList;
import java.util.List;

public class DistributorController implements Subject {

    private final ObservableList<Company> companies = FXCollections.observableArrayList();
    private final ObservableList<Company> tripsPerCompany = FXCollections.observableArrayList();
    private final ObservableList<Trip> availableTrips = FXCollections.observableArrayList();
    @FXML
    private Parent checkRequests;
    @FXML
    private RequestPanelController requestPanelController;
    @FXML
    private ListView<Company> companiesListView;
    @FXML
    private ListView<Trip> companyTripsListView;
    @FXML
    private ListView<Trip> availableTripsListView;
    @FXML
    private TextField cashierName;
    private String currentDistributor;
    private CompanyController companyController;
    private CompanyService companyService;
    private TripService tripService;
    private CashierService cashierService;
    private DistributorService distributorService;
    private TicketService ticketService;

    private Observer observer;
    private Command command;

    @FXML
    public void showInfo() {
        companiesListView.getItems().clear();
        companyTripsListView.getItems().clear();
        availableTripsListView.getItems().clear();

        companiesListView.getItems().addAll(companyService.getAllCompanies());
        availableTripsListView.getItems().addAll(distributorService
                .tripPerDistributor(distributorService
                        .getDistributorByName(currentDistributor)));
    }

    @FXML
    public void respondToRequest(Command command) {
        requestPanelController.addCommand(command);
    }

    @FXML
    public void requestTrip() {
        Trip selectedTrip = companyTripsListView.getSelectionModel().getSelectedItem();
        if (selectedTrip != null) {
            if (selectedTrip.getDistributor() != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert!");
                alert.setHeaderText("Trip is managed!");
                alert.setContentText("This trip is already managed by another" +
                        " distributor.");
                alert.showAndWait();
                return;
            }
            String message = "Would you like to accept a trip request to "
                    + currentDistributor + ": " + selectedTrip + " ?";
            List<Object> selectedTripList = new ArrayList<>();
            selectedTripList.add(selectedTrip);
            command = new RequestToCompanyCommandImpl(
                    message,
                    selectedTripList,
                    companyController,
                    distributorService.getDistributorByName(currentDistributor)
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

    private ObservableList<Trip> getTripsForCompany(Company company) {
        List<Trip> tripList = company.getTrips();
        return FXCollections.observableArrayList(tripList);
    }

    @FXML
    public void loadCompanyTrips() {
        Company selectedCompany = companiesListView.getSelectionModel().getSelectedItem();
        if (selectedCompany != null) {
            companyTripsListView.getItems().clear();
            companyTripsListView.setItems(getTripsForCompany(selectedCompany));
        }
    }

    @FXML
    public void initialize() {
        try {
            FXMLLoader requestPanelLoader = new FXMLLoader(getClass().getResource("/org/tuvarna/olekalekproject/check-requests-distributor.fxml"));
            checkRequests = requestPanelLoader.load();
            requestPanelController = requestPanelLoader.<DistrToCashPanelControllerImpl>getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
     }


    @FXML
    public void createCashier() {
        Cashier cashier = new Cashier();
        cashier.setName(cashierName.getText());
        cashier.setDistributor(distributorService
                .getDistributorByName(currentDistributor));
        cashierService.addCashier(cashier);
        notifyObservers();
    }

//    public boolean requestTrip() { //execute command
//        return this.command.execute();
//    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public ListView<Company> getCompaniesListView() {
        return companiesListView;
    }

    public void setCompaniesListView(ListView<Company> companiesListView) {
        this.companiesListView = companiesListView;
    }

    public ListView<Trip> getCompanyTripsListView() {
        return companyTripsListView;
    }

    public void setCompanyTripsListView(ListView<Trip> companyTripsListView) {
        this.companyTripsListView = companyTripsListView;
    }

    public ListView<Trip> getAvailableTripsListView() {
        return availableTripsListView;
    }

    public void setAvailableTripsListView(ListView<Trip> availableTripsListView) {
        this.availableTripsListView = availableTripsListView;
    }

    public TextField getCashierName() {
        return cashierName;
    }

    public void setCashierName(TextField cashierName) {
        this.cashierName = cashierName;
    }

    public ObservableList<Company> getCompanies() {
        return companies;
    }

    public ObservableList<Trip> getAvailableTrips() {
        return availableTrips;
    }

    public CompanyService getCompanyService() {
        return companyService;
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public TripService getTripService() {
        return tripService;
    }

    public void setTripService(TripService tripService) {
        this.tripService = tripService;
    }

    public CashierService getCashierService() {
        return cashierService;
    }

    public void setCashierService(CashierService cashierService) {
        this.cashierService = cashierService;
    }

    public Parent getCheckRequests() {
        return checkRequests;
    }

    public void setCheckRequests(Parent checkRequests) {
        this.checkRequests = checkRequests;
    }

    public RequestPanelController getRequestPanelController() {
        return requestPanelController;
    }

    public void setRequestPanelController(RequestPanelController requestPanelController) {
        this.requestPanelController = requestPanelController;
    }

    public ObservableList<Company> getTripsPerCompany() {
        return tripsPerCompany;
    }

    public DistributorService getDistributorService() {
        return distributorService;
    }

    public void setDistributorService(DistributorService distributorService) {
        this.distributorService = distributorService;
    }

    public String getCurrentDistributor() {
        return currentDistributor;
    }

    public void setCurrentDistributor(String currentDistributor) {
        this.currentDistributor = currentDistributor;
    }

    public TicketService getTicketService() {
        return ticketService;
    }

    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public void registerObserver(Observer observer) {
        this.observer = observer; //main controller
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observer = null;
    }

    public CompanyController getCompanyController() {
        return companyController;
    }

    public void setCompanyController(CompanyController companyController) {
        this.companyController = companyController;
    }

    // code example for administrator controller
    @Override
    public void notifyObservers() {
        List<String> values = new ArrayList<>();
        values.add(cashierName.getText());
        values.add("Cashiers");
        this.observer.update(values);
    }
}
