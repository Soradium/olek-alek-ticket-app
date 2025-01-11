package org.tuvarna.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.command.Command;
import org.tuvarna.database.DatabaseSingleton;
import org.tuvarna.entity.*;

import org.tuvarna.observer.Observer;
import org.tuvarna.observer.Subject;
import org.tuvarna.service.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CompanyController implements Subject {
    @FXML
    public ListView<Bus> busListView;
    @FXML
    private Parent checkRequests; //requestPanel
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

    private DistributorService distributorService;

    private TicketService ticketService;

    private final SessionFactory sessionFactory = DatabaseSingleton.getInstance().getSessionFactory();

    public void setTicketService(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public void setBusService(BusService busService) {
        this.busService = busService;
    }

    public void setCompanyService(CompanyService companyService) {
        this.companyService = companyService;
    }

    public void setTripService(TripService tripService) {
        this.tripService = tripService;
    }

    private final ObservableList<Trip> trips = FXCollections.observableArrayList();

    private final ObservableList<Bus> buses = FXCollections.observableArrayList();

    private final ObservableList<Command> commands = FXCollections.observableArrayList();

    private Observer observer;

    private Company getCurrentCompany() {
        return companyService.getCompanyByName(tripService.getCurrentCompanyName());
    }

    public void initialize() {
        try {
            FXMLLoader requestPanelLoader = new FXMLLoader(getClass().getResource("/org/tuvarna/olekalekproject/check-requests-company.fxml"));
            checkRequests = requestPanelLoader.load();
            requestPanelController = requestPanelLoader.<CompToDistrPanelControllerImpl>getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void respondToRequest(Command command) {
        requestPanelController.addCommand(command);
    }

    @FXML
    public void addTrip(){
        String departureText = departure.getText();
        String destinationText = destination.getText();
        LocalDate timeOfDepartureText = null;
        if(!timeOfDeparture.getText().isEmpty()){
            timeOfDepartureText = LocalDate.parse(timeOfDeparture.getText());
        }
        String tripTypeText = tripType.getText();
        System.out.println("Local date: " + timeOfDepartureText);
        Bus currentBus = busListView.getSelectionModel().getSelectedItem();
        if(departureText.isEmpty() ||
                destinationText.isEmpty() ||
                timeOfDepartureText == null ||
                tripTypeText.isEmpty() ||
                currentBus == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Creating error");
            StringBuilder sb = new StringBuilder("Enter next fields: ");
            boolean first = true;
            if(departureText.isEmpty()) {
                if(!first) sb.append(", ");
                sb.append("Departure");
                first = false;
            }
            if(destinationText.isEmpty()) {
                if(!first) sb.append(", ");
                sb.append("Destination");
                first = false;
            }
            if(timeOfDepartureText == null) {
                if(!first) sb.append(", ");
                sb.append("Time of Departure");
                first = false;
            }
            if(tripTypeText.isEmpty()) {
                if(!first) sb.append(", ");
                sb.append("Trip type");
                first = false;
            }
            if(currentBus == null) {
                if(!first) sb.append(", ");
                sb.append("Choose bus");
                first = false;
            }
            alert.setContentText(sb.toString());
            alert.showAndWait();
            return;
        }
        System.out.println(currentBus.getId());
        tripService.addTrip(new Trip(departureText,
                destinationText,
                timeOfDepartureText,
                tripTypeText,
                getCurrentCompany(),
                busListView.getSelectionModel().getSelectedItem()));
        changeAvailabiltyOfBus(busListView.getSelectionModel().getSelectedItem());
        departure.clear();
        destination.clear();
        timeOfDeparture.clear();
        tripType.clear();
    }

    private void changeAvailabiltyOfBus(Bus bus){
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createQuery("update Bus set available = false where id =:id").
                setParameter("id", bus.getId()).
                executeUpdate();
        session.getTransaction().commit();
    }
    @FXML
    public void addBus(){
        busService.addBus(new Bus(getCurrentCompany()));
    }

    public Parent getCheckRequests() {
        return checkRequests;
    }

    public void setCheckRequests(Parent checkRequests) {
        this.checkRequests = checkRequests;
    }

    @FXML
    private void showInfo() {
        tripListView.getItems().clear();
        trips.addAll(tripService.getAllTripsByCompany());
        ratingLabel.setText(String.valueOf(getCurrentCompany().getCurrentRating()));
        tripListView.setItems(trips);
        tripListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Trip item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color:  #cfd4d2; -fx-font-weight: bold; -fx-font-size: 14px;"); // Сбрасываем стиль для пустых ячеек
                } else {
                    setText(item.toString());
                    if(isSelected()){
                        setStyle("-fx-background-color:  #6d6d6e; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: black;");
                    }else{
                        setStyle("-fx-background-color:  #cfd4d2; -fx-font-weight: bold; -fx-font-size: 14px;");

                    }
                }
            }
        });
        busListView.getItems().clear();
        buses.addAll(busService.getAllAvailableBusesByCompany());
        busListView.setItems(buses);
        busListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Bus item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color:  #cfd4d2; -fx-font-weight: bold; -fx-font-size: 14px;"); // Сбрасываем стиль для пустых ячеек
                } else {
                    setText(item.toString());
                    if(isSelected()){
                        setStyle("-fx-background-color: #6d6d6e; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: black");
                    }else{
                        setStyle("-fx-background-color:  #cfd4d2; -fx-font-weight: bold; -fx-font-size: 14px;");
                    }
                }
            }
        });
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

    public RequestPanelController getRequestPanelController() {
        return requestPanelController;
    }

    public void setRequestPanelController(RequestPanelController requestPanelController) {
        this.requestPanelController = requestPanelController;
    }

    public DistributorService getDistributorService() {
        return distributorService;
    }

    public void setDistributorService(DistributorService distributorService) {
        this.distributorService = distributorService;
    }
}
