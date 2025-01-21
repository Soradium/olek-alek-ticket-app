package org.tuvarna.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.command.Command;
import org.tuvarna.command.RequestToCashierCommandImpl;
import org.tuvarna.database.DatabaseSingleton;
import org.tuvarna.entity.CompanyRatings;
import org.tuvarna.entity.Ticket;
import org.tuvarna.entity.Trip;
import org.tuvarna.service.TicketService;
import org.tuvarna.service.TripService;
import org.tuvarna.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserController {

    private final static Logger logger = LogManager.getLogger(UserController.class);
    private final SessionFactory sessionFactory = DatabaseSingleton.getInstance().getSessionFactory();
    private final SimpleStringProperty currentUser = new SimpleStringProperty();
    @FXML
    public ComboBox<Trip> tripComboBox;
    @FXML
    public ComboBox<Ticket> seatComboBox;
    @FXML
    public ComboBox<Ticket> purchasedTripsComboBox;
    @FXML
    public RadioButton rating1, rating2, rating3, rating4, rating5;
    @FXML
    public ToggleGroup ratingGroup;
    @FXML
    public Label errorLabel;
    private Command command;
    private CashierController cashierController;
    private UserService userService;
    private TripService tripService;
    private TicketService ticketService;

    @FXML
    public void initialize() {
        ratingGroup = new ToggleGroup();
        rating1.setToggleGroup(ratingGroup);
        rating2.setToggleGroup(ratingGroup);
        rating3.setToggleGroup(ratingGroup);
        rating4.setToggleGroup(ratingGroup);
        rating5.setToggleGroup(ratingGroup);
        logger.info("Successfully configured rating group");
    }

    public void initializeData() {
        List<Trip> trips = tripService.getAllTrips().stream().filter(c -> c.getDistributor() != null
                        && c.getCashier() != null).
                toList();
        logger.info("Successfully retrieved all trips with distributor and cashier");
        ObservableList<Trip> obsTrips = FXCollections.observableArrayList(trips);
        tripComboBox.setItems(obsTrips);
        tripComboBox.setValue(obsTrips.get(0));
        showTickets();
        tripComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showTickets();
            } else {
                seatComboBox.getItems().clear();
            }
        });
        logger.info("Successfully retrieved all tickets for trip: {}", tripComboBox.getSelectionModel().getSelectedItem());
        currentUser.addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showTicketsForCurrentUser();
            } else {
                purchasedTripsComboBox.getItems().clear();
            }
        });
        logger.info("Successfully retrieved all tickets for user: {}", currentUser.get());
    }

    public void submitRating(ActionEvent actionEvent) {
        Trip currentTrip = purchasedTripsComboBox.getSelectionModel().getSelectedItem().getTrip();
        Ticket currentTicket = purchasedTripsComboBox.getSelectionModel().getSelectedItem();
        logger.info("Current ticket: {}", currentTicket);
        if (ticketService.getTicketById(currentTicket.getId()).isRate()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert!");
            alert.setHeaderText("Rated Trip");
            alert.setContentText("You already rate this trip.");
            alert.showAndWait();
            logger.info("Rated Trip");
            return;
        }
        RadioButton selectedRadioButton = (RadioButton) ratingGroup.getSelectedToggle();
        String rating = selectedRadioButton.getText();
        logger.info("Selected rating: {}", rating);
        logger.info("Retrieving current session");
        Session session = sessionFactory.getCurrentSession();
        logger.info("Begin transaction");
        session.beginTransaction();
        try {
            session.merge(new CompanyRatings(currentTrip.getCompany(), Integer.parseInt(rating)));
            session.createQuery("update Ticket set isRate = true where id = :id").
                    setParameter("id", currentTicket.getId()).
                    executeUpdate();
            session.getTransaction().commit();
            logger.info("Successfully updated current ticket and company ratings table");
            errorLabel.setVisible(false);
        } catch (Exception e) {
            logger.error("Occurred error in submitRating method, with message: {}", e.getMessage());
        } finally {
            session.close();
        }
    }

    private void showTickets() {
        Trip currentTrip = tripComboBox.getSelectionModel().getSelectedItem();
        List<Ticket> tickets = ticketService.getAllTickets().
                stream().
                filter(c -> c.getTrip().getId() == currentTrip.getId()
                        && !c.isSold() && c.getDistributor() != null)
                .toList();
        logger.info("Filtered tickets for trip: {}", currentTrip);
        ObservableList<Ticket> obsTickets = FXCollections.observableArrayList(tickets);
        seatComboBox.setItems(obsTickets);
    }

    private void showTicketsForCurrentUser() {
        List<Ticket> tickets = ticketService.getAllTickets().
                stream().
                filter(c -> c.getUser() != null
                        && c.getUser().
                        getName().
                        equals(currentUser.get())).
                toList();
        logger.info("Filtered tickets for user: {}", currentUser);
        ObservableList<Ticket> obsTickets = FXCollections.observableArrayList(tickets);
        purchasedTripsComboBox.setItems(obsTickets);
    }

    public void ticketOrder() {
        try {
            Ticket selectedTicket = seatComboBox.getSelectionModel().getSelectedItem();
            logger.info("Selected ticket: {}", selectedTicket);
            if (selectedTicket != null) {
                logger.info("Checking the existence of a user");
                if (selectedTicket.getUser() != null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Alert!");
                    alert.setHeaderText("Ticket is sold!");
                    alert.setContentText("Ticket already purchased by another user!");
                    alert.showAndWait();
                    logger.info("Ticket is purchased by another user");
                    return;
                }
                String message = "Would you like to accept a ticket request to "
                        + currentUser.get() + ": " + selectedTicket + " ?";
                List<Object> selectedTripList = new ArrayList<>();
                selectedTripList.add(selectedTicket);
                command = new RequestToCashierCommandImpl(
                        message,
                        selectedTripList,
                        cashierController,
                        userService.getUserByName(currentUser.get())
                );
                logger.info("Successfully accepted a ticket request");
                command.execute();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success!");
                alert.setHeaderText("Ticket was requested.");
                alert.setContentText("Request was sent to corresponding cashier.");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert!");
                alert.setHeaderText("Ticket was not chosen!");
                alert.setContentText("Request was not sent.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            logger.error("Error during initialize of ticket request. Error: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public void setTripService(TripService tripService) {
        this.tripService = tripService;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser.set(currentUser);
    }

    public void setCashierController(CashierController cashierController) {
        this.cashierController = cashierController;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
