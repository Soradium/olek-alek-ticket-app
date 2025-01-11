package org.tuvarna.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.control.Rating;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.command.Command;
import org.tuvarna.command.RequestToCashierCommandImpl;
import org.tuvarna.command.RequestToDistributorCommandImpl;
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

    private final SessionFactory sessionFactory = DatabaseSingleton.getInstance().getSessionFactory();

    private final SimpleStringProperty currentUser = new SimpleStringProperty();

    private TripService tripService;

    private TicketService ticketService;

    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public void setTripService(TripService tripService) {
        this.tripService = tripService;
    }

    @FXML
    public void initialize() {
        ratingGroup = new ToggleGroup();
        rating1.setToggleGroup(ratingGroup);
        rating2.setToggleGroup(ratingGroup);
        rating3.setToggleGroup(ratingGroup);
        rating4.setToggleGroup(ratingGroup);
        rating5.setToggleGroup(ratingGroup);
    }

    public void initializeData(){
        List<Trip> trips = tripService.getAllTrips();
        ObservableList<Trip> obsTrips = FXCollections.observableArrayList(trips);
        tripComboBox.setItems(obsTrips);
        tripComboBox.setValue(obsTrips.get(0));
        showTickets();
        tripComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                showTickets();
            }else{
                seatComboBox.getItems().clear();
            }
        });
        System.out.println("Current user=" + currentUser);
        currentUser.addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                showTicketsForCurrentUser();
            }else{
                purchasedTripsComboBox.getItems().clear();
            }
        });
    }


    public void submitRating(ActionEvent actionEvent) {
        Trip currentTrip = purchasedTripsComboBox.getSelectionModel().getSelectedItem().getTrip();
        Ticket currentTicket = purchasedTripsComboBox.getSelectionModel().getSelectedItem();
        if(ticketService.getTicketById(currentTicket.getId()).isRate()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Alert!");
            alert.setHeaderText("Rated Trip");
            alert.setContentText("You already rate this trip.");
            alert.showAndWait();
            return;
        }
        RadioButton selectedRadioButton = (RadioButton) ratingGroup.getSelectedToggle();
        String rating = selectedRadioButton.getText();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        if(currentTicket.isRate()){
            errorLabel.setVisible(true);
            return;
        }
        try{
            session.merge(new CompanyRatings(currentTrip.getCompany(), Integer.parseInt(rating)));
            session.createQuery("update Ticket set isRate = true where id = :id").
                    setParameter("id", currentTicket.getId()).
                    executeUpdate();
            session.getTransaction().commit();
            errorLabel.setVisible(false);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public SimpleStringProperty getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser.set(currentUser);
    }

    private void showTickets() {
        Trip currentTrip = tripComboBox.getSelectionModel().getSelectedItem();
        List<Ticket> tickets = ticketService.getAllTickets().stream().filter(c -> c.getTrip().getId() == currentTrip.getId() && !c.isSold() && c.getDistributor() != null).toList();
        ObservableList<Ticket> obsTickets = FXCollections.observableArrayList(tickets);
        System.out.println(obsTickets);
        seatComboBox.setItems(obsTickets);
        System.out.println(currentTrip);
    }

    private void showTicketsForCurrentUser() {
        List<Ticket> tickets = ticketService.getAllTickets().stream().filter(c -> c.getUser() != null &&  c.getUser().getName().equals(currentUser.get())).toList();
        ObservableList<Ticket> obsTickets = FXCollections.observableArrayList(tickets);
        System.out.println(obsTickets);
        purchasedTripsComboBox.setItems(obsTickets);
        System.out.println(currentUser);
    }

    public void ticketOrder() {
        Ticket selectedTicket = seatComboBox.getSelectionModel().getSelectedItem();
        if (selectedTicket != null) {
            if (selectedTicket.getUser() != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Alert!");
                alert.setHeaderText("Ticket is sold!");
                alert.setContentText("Ticket already purchased by another user!");
                alert.showAndWait();
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
    }

    public CashierController getCashierController() {
        return cashierController;
    }

    public void setCashierController(CashierController cashierController) {
        this.cashierController = cashierController;
    }

    public TicketService getTicketService() {
        return ticketService;
    }

    public TripService getTripService() {
        return tripService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
