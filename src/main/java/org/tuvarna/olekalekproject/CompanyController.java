package org.tuvarna.olekalekproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.tuvarna.entity.Trip;

import java.util.ArrayList;
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

    private ObservableList<Trip> trips = FXCollections.observableArrayList();
    private Session session;

    public void setSession(Session session) {
        this.session = session;
        loadTrips();
    }

    @FXML
    public void addTrip(){
        String departureText = departure.getText();
        String destinationText = destination.getText();
        String timeOfDepartureText = timeOfDeparture.getText();
        String tripTypeText = tripType.getText();
        if(session == null){
            System.out.println("session is null");
        }
        session.beginTransaction();
        try{
            Trip trip = new Trip(departureText, destinationText, timeOfDepartureText, tripTypeText);
            session.persist(trip);
            trips.add(trip);
            session.getTransaction().commit();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            session.close();
        }

        departure.clear();
        destination.clear();
        timeOfDeparture.clear();
        tripType.clear();
    }

    public void addBus(){

    }

    private void loadTrips() {
        session.beginTransaction();
        try {
            List<Trip> allTrips = session.createQuery("from Trip", Trip.class).getResultList();
            trips.addAll(allTrips);
            tripListView.setItems(trips);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
