package org.tuvarna.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import org.controlsfx.control.Rating;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.database.DatabaseSingleton;
import org.tuvarna.entity.CompanyRatings;
import org.tuvarna.entity.Ticket;
import org.tuvarna.entity.Trip;
import org.tuvarna.service.TicketService;
import org.tuvarna.service.TripService;

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
        Session session = sessionFactory.getCurrentSession();
        Trip currentTrip = purchasedTripsComboBox.getSelectionModel().getSelectedItem().getTrip();
        Ticket currentTicket = purchasedTripsComboBox.getSelectionModel().getSelectedItem();
        RadioButton selectedRadioButton = (RadioButton) ratingGroup.getSelectedToggle();
        String rating = selectedRadioButton.getText();
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
}
