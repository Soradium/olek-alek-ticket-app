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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.tuvarna.command.Command;
import org.tuvarna.controller.report_panel.CompanyReport;
import org.tuvarna.controller.report_panel.ReportController;
import org.tuvarna.controller.request_panel.CompToDistrPanelControllerImpl;
import org.tuvarna.controller.request_panel.RequestPanelController;
import org.tuvarna.database.DatabaseSingleton;
import org.tuvarna.entity.Bus;
import org.tuvarna.entity.Cashier;
import org.tuvarna.entity.Company;
import org.tuvarna.entity.Trip;
import org.tuvarna.observer.Observer;
import org.tuvarna.observer.Subject;
import org.tuvarna.service.BusService;
import org.tuvarna.service.CompanyService;
import org.tuvarna.service.TripService;

import java.time.LocalDate;

public class CompanyController implements Subject, Cloneable {
    private static final Logger logger = LogManager.getLogger(CompanyController.class);
    private final SessionFactory sessionFactory = DatabaseSingleton.getInstance().getSessionFactory();
    private final ObservableList<Trip> trips = FXCollections.observableArrayList();
    private final ObservableList<Bus> buses = FXCollections.observableArrayList();
    private final SimpleStringProperty currentCompanyProperty = new SimpleStringProperty();
    @FXML
    public ListView<Bus> busListView;
    @FXML
    public Label companyName;
    @FXML
    private Parent checkRequests;
    @FXML
    private Parent reportsPanel;
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
    @FXML
    private ReportController reportController;
    private TripService tripService;
    private CompanyService companyService;
    private BusService busService;
    private Observer observer;

    @FXML
    public void initialize() {
        try {
            logger.info("Try to add requestPanel for {}", CompanyController.class.getSimpleName());
            FXMLLoader requestPanelLoader = new FXMLLoader(getClass().getResource("/org/tuvarna/olekalekproject/check-requests-company.fxml"));
            FXMLLoader reportPanelLoader = new FXMLLoader(getClass().getResource("/org/tuvarna/olekalekproject/reports-company.fxml"));
            checkRequests = requestPanelLoader.load();
            requestPanelLoader.<CompToDistrPanelControllerImpl>getController().setCompanyController(this);
            requestPanelController = requestPanelLoader.<CompToDistrPanelControllerImpl>getController();
            reportsPanel = reportPanelLoader.load();
            reportPanelLoader.<CompanyReport>getController().setCompanyController(this);
            reportController = reportPanelLoader.getController();
            logger.info("Successfully loaded check-requests-cashier.fxml");
        } catch (Exception e) {
            logger.error("Error during initialize of check-requests-company. Error: {}", e.getMessage());
        }
    }

    public void initializeData(String companyNameText) {
        logger.info("Initializing data. Current company is: {}", getCurrentCompany());
        setCurrentCompanyProperty(companyNameText);
        companyName.setText(companyNameText);
    }

    @FXML
    public void respondToRequest(Command command) {
        requestPanelController.addCommand(command);
        logger.info("Successfully respond to request");
    }

    @FXML
    public void addTrip() {
        String departureText = departure.getText();
        String destinationText = destination.getText();
        LocalDate timeOfDepartureText = null;
        if (!timeOfDeparture.getText().isEmpty()) {
            timeOfDepartureText = LocalDate.parse(timeOfDeparture.getText());
        }
        String tripTypeText = tripType.getText();
        Bus currentBus = busListView.getSelectionModel().getSelectedItem();
        logger.info("Parsing data from text fields");
        logger.info("Checking of correct information");
        if (departureText.isEmpty() ||
                destinationText.isEmpty() ||
                timeOfDepartureText == null ||
                tripTypeText.isEmpty() ||
                currentBus == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Creating error");
            StringBuilder sb = new StringBuilder("Enter next fields: ");
            boolean first = true;
            if (departureText.isEmpty()) {
                if (!first) sb.append(", ");
                sb.append("Departure");
                first = false;
            }
            if (destinationText.isEmpty()) {
                if (!first) sb.append(", ");
                sb.append("Destination");
                first = false;
            }
            if (timeOfDepartureText == null) {
                if (!first) sb.append(", ");
                sb.append("Time of Departure");
                first = false;
            }
            if (tripTypeText.isEmpty()) {
                if (!first) sb.append(", ");
                sb.append("Trip type");
                first = false;
            }
            if (currentBus == null) {
                if (!first) sb.append(", ");
                sb.append("Choose bus");
                first = false;
            }
            alert.setContentText(sb.toString());
            alert.showAndWait();
            logger.info("Error message because blank fields");
            return;
        }
        if (timeOfDepartureText.isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("Error");
            alert.setContentText("Invalid time of departure");
            alert.showAndWait();
            logger.info("Error message because of incorrect data entered");
            return;
        }
        tripService.addTrip(new Trip(departureText,
                destinationText,
                timeOfDepartureText,
                tripTypeText,
                getCurrentCompany(),
                busListView.getSelectionModel().getSelectedItem()));
        logger.info("Successfully added trip");
        changeAvailabiltyOfBus(busListView.getSelectionModel().getSelectedItem());
        departure.clear();
        destination.clear();
        timeOfDeparture.clear();
        tripType.clear();
        logger.info("Fields clean up");
    }

    private void changeAvailabiltyOfBus(Bus bus) {
        logger.info("Session receiving");
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        logger.info("Start of transaction");
        try {
            session.createQuery("update Bus set available = false where id =:id").
                    setParameter("id", bus.getId()).
                    executeUpdate();
            session.getTransaction().commit();
            logger.info("End of transaction. Successfully updated bus");
        } catch (Exception e) {
            logger.error("Error during transaction in function changeAvailabilityOfBus. Error: {}", e.getMessage());
        }
    }

    @FXML
    public void addBus() {
        busService.addBus(new Bus(getCurrentCompany()));
        logger.info("Successfully added bus");
    }

    @FXML
    private void showInfo() {
        logger.info("tripListView cleaning");
        tripListView.getItems().clear();
        trips.addAll(tripService.getAllTripsByCompany());
        logger.info("All trips by company: {}", trips.size());
        ratingLabel.setText(String.valueOf(getCurrentCompany().getCurrentRating()));
        logger.info("Successfully current rating receiving");
        tripListView.setItems(trips);
        tripListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Trip item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color:  #f4f4f4; -fx-font-size: 14px;");
                } else {
                    Label label = new Label(item.toString());
                    label.setWrapText(true);
                    label.setStyle("-fx-font-size: 14px;");

                    label.setMaxWidth(500);
                    if (isSelected()) {
                        label.setStyle("-fx-background-color:  #cbcbcb;-fx-font-size: 14px; -fx-text-fill: black;");
                    } else {
                        label.setStyle("-fx-background-color: #f4f4f4; -fx-font-size: 14px;");

                    }
                    setGraphic(label);
                }
            }
        });
        logger.info("Configuring cellFactory for tripListView");
        logger.info("busListView cleaning");
        busListView.getItems().clear();
        buses.addAll(busService.getAllAvailableBusesByCompany());
        logger.info("All buses by company: {}", buses.size());
        busListView.setItems(buses);
        busListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Bus item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-background-color:  #f4f4f4; -fx-font-size: 14px;"); // Сбрасываем стиль для пустых ячеек
                } else {
                    setText(item.toString());
                    if (isSelected()) {
                        setStyle("-fx-background-color: #cbcbcb; -fx-font-size: 14px; -fx-text-fill: black");
                    } else {
                        setStyle("-fx-background-color:  #f4f4f4; -fx-font-size: 14px;");
                    }
                }
            }
        });
        logger.info("Configuring cellFactory for busListView");
    }

    @Override
    public void registerObserver(Observer observer) {
        this.observer = observer;
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observer = null;
    }

    @Override
    public void notifyObservers() {
        this.observer.update(getCurrentCompany().getName());
    }

    @Override
    protected CompanyController clone() throws CloneNotSupportedException {
        try {
            return (CompanyController) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("superclass messed up", ex);
        }
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

    public void setCurrentCompanyProperty(String currentCompanyProperty) {
        this.currentCompanyProperty.set(currentCompanyProperty);
    }

    public Parent getCheckRequests() {
        return checkRequests;
    }

    public Company getCurrentCompany() {
        return companyService.getCompanyByName(tripService.getCurrentCompanyName());
    }

    public Parent getReportsPanel() {
        return reportsPanel;
    }

    public void setReportsPanel(Parent reportsPanel) {
        this.reportsPanel = reportsPanel;
    }

    public TripService getTripService() {
        return tripService;
    }

    public void setDeparture(TextField departure) {
        this.departure = departure;
    }

    public void setDestination(TextField destination) {
        this.destination = destination;
    }

    public void setTimeOfDeparture(TextField timeOfDeparture) {
        this.timeOfDeparture = timeOfDeparture;
    }

    public void setTripType(TextField tripType) {
        this.tripType = tripType;
    }

    public void setBusListView(ListView<Bus> busListView) {
        this.busListView = busListView;
    }


}
