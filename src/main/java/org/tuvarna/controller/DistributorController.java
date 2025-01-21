package org.tuvarna.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger logger = LogManager.getLogger(DistributorController.class);
    private final SimpleStringProperty currentDistributorProperty = new SimpleStringProperty();
    private final ObservableList<Trip> tripsPerCompany = FXCollections.observableArrayList();
    @FXML
    public Label distributorName;
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
    private ObservableList<Company> companies = FXCollections.observableArrayList();
    private ObservableList<Trip> availableTrips = FXCollections.observableArrayList();
    private Observer observer;
    private Command command;

    @FXML
    public void showInfo() {
        companiesListView.getItems().clear();
        companyTripsListView.getItems().clear();
        availableTripsListView.getItems().clear();
        logger.info("Clearing all fields");

        companies.clear();
        companies = FXCollections.observableArrayList(companyService.getAllCompanies());
        logger.info("All companies: {}", companies.size());
        companiesListView.setItems(companies);
        logger.info("Companies in companiesListView: {}", companiesListView.getSelectionModel().getSelectedItems().size());
        companiesListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Company item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("-fx-background-color:  #f4f4f4; -fx-font-size: 14px;");
                } else {
                    Label label = new Label(item.toString());
                    label.setWrapText(true);
                    label.setStyle("-fx-font-size: 14px;");

                    label.setMaxWidth(companiesListView.getWidth());
                    if (isSelected()) {
                        label.setStyle("-fx-background-color:  #cbcbcb;-fx-font-size: 14px; -fx-text-fill: black;");
                    } else {
                        label.setStyle("-fx-background-color: #f4f4f4; -fx-font-size: 14px;");

                    }
                    setGraphic(label);
                }
            }
        });
        logger.info("Configuration cellFactory for companies list view");
        availableTrips = FXCollections.observableArrayList(distributorService
                .tripPerDistributor(distributorService
                        .getDistributorByName(currentDistributor)));
        logger.info("Available trips: {}", availableTrips.size());
        availableTripsListView.setItems(availableTrips);
        logger.info("Available trips in availableTripsListView: {}", availableTripsListView.getSelectionModel().getSelectedItems().size());
        availableTripsListView.setCellFactory(lv -> new ListCell<>() {
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

                    label.setMaxWidth(availableTripsListView.getWidth());
                    if (isSelected()) {
                        label.setStyle("-fx-background-color:  #cbcbcb;-fx-font-size: 14px; -fx-text-fill: black;");
                    } else {
                        label.setStyle("-fx-background-color: #f4f4f4; -fx-font-size: 14px;");

                    }
                    setGraphic(label);
                }
            }
        });
        logger.info("Configuration cellFactory for tripsListView");
    }

    @FXML
    public void respondToRequest(Command command) {
        requestPanelController.addCommand(command);
        logger.info("Successfully respond to request");
    }

    @FXML
    public void requestTrip() {
        try {
            Trip selectedTrip = companyTripsListView.getSelectionModel().getSelectedItem();
            logger.info("Selected trip: {}", selectedTrip);
            if (selectedTrip != null) {
                logger.info("Checking the existence of a cashier");
                if (selectedTrip.getDistributor() != null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Alert!");
                    alert.setHeaderText("Trip is managed!");
                    alert.setContentText("This trip is already managed by another" +
                            " distributor.");
                    alert.showAndWait();
                    logger.info("Trip is managed by another cashier");
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
                logger.info("Successfully accepted a trip request");
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
        } catch (Exception e) {
            logger.error("Error during initialize of trip request. Error: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    private ObservableList<Trip> getTripsForCompany(Company company) {
        List<Trip> tripList = company.getTrips();
        logger.info("All trips for company: {}", tripList.size());
        return FXCollections.observableArrayList(tripList);
    }

    @FXML
    public void loadCompanyTrips() {
        Company selectedCompany = companiesListView.getSelectionModel().getSelectedItem();
        logger.info("Selected company: {}", selectedCompany);
        if (selectedCompany != null) {
            companyTripsListView.getItems().clear();
            logger.info("Clearing of companyTripsListView items");
            tripsPerCompany.clear();
            tripsPerCompany.addAll(getTripsForCompany(selectedCompany));
            logger.info("All trips by selected company: {}", tripsPerCompany.size());
            companyTripsListView.setItems(tripsPerCompany);
            logger.info("Trips in companyTripsListView: {}", tripsPerCompany.size());
            companyTripsListView.setCellFactory(lv -> new ListCell<>() {
                @Override
                protected void updateItem(Trip item, boolean empty) {
                    super.updateItem(item, empty);

                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                        setStyle("-fx-background-color:  #f4f4f4; -fx-font-size: 14px;");
                    } else {
                        Label label = new Label(item.toString());
                        label.setStyle("-fx-font-size: 14px;");
                        label.setWrapText(true);

                        label.setMaxWidth(companyTripsListView.getWidth());
                        if (isSelected()) {
                            label.setStyle("-fx-background-color:  #cbcbcb;-fx-font-size: 14px; -fx-text-fill: black;");
                        } else {
                            label.setStyle("-fx-background-color: #f4f4f4; -fx-font-size: 14px;");

                        }
                        setGraphic(label);
                    }
                }
            });
            logger.info("Configured cellFactory for companyTripsListView");
        }
    }

    @FXML
    public void initialize() {
        try {
            logger.info("Try to add requestPanel for {}", CashierController.class.getSimpleName());
            FXMLLoader requestPanelLoader = new FXMLLoader(getClass().getResource("/org/tuvarna/olekalekproject/check-requests-distributor.fxml"));
            checkRequests = requestPanelLoader.load();
            requestPanelLoader.<DistrToCashPanelControllerImpl>getController().setDistributorController(this);
            requestPanelController = requestPanelLoader.<DistrToCashPanelControllerImpl>getController();
            logger.info("Successfully loaded check-requests-distributor.fxml");
        } catch (Exception e) {
            logger.error("Error during initialize of check-requests-distributor. Error: {}", e.getMessage());
        }
    }

    public void initializeData(String currentDistributorText) {
        setCurrentDistributorProperty(currentDistributorText);
        distributorName.setText(currentDistributorText);
        logger.info("Setting current distributor: {}", currentDistributorText);
    }

    @FXML
    public void createCashier() {
        logger.info("Creating new cashier");
        Cashier cashier = new Cashier();
        cashier.setName(cashierName.getText());
        cashier.setDistributor(distributorService
                .getDistributorByName(currentDistributor));
        cashierService.addCashier(cashier);
        logger.info("Successfully created cashier");
        notifyObservers();
    }

    public void setCurrentDistributorProperty(String currentDistributorProperty) {
        this.currentDistributorProperty.set(currentDistributorProperty);
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void setTripService(TripService tripService) {
        this.tripService = tripService;
    }

    public void setCashierService(CashierService cashierService) {
        this.cashierService = cashierService;
    }

    public Parent getCheckRequests() {
        return checkRequests;
    }

    public void setDistributorService(DistributorService distributorService) {
        this.distributorService = distributorService;
    }

    public void setCompanyController(CompanyController companyController) {
        this.companyController = companyController;
    }

    public void setCurrentDistributor(String currentDistributor) {
        this.currentDistributor = currentDistributor;
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

}
