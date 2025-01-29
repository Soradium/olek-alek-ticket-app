package org.tuvarna.controller;

import javafx.application.Platform;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit.ApplicationTest;
import org.tuvarna.entity.Bus;
import org.tuvarna.entity.Company;
import org.tuvarna.entity.Trip;
import org.tuvarna.service.CompanyService;
import org.tuvarna.service.TripService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CompanyControllerTst extends ApplicationTest {
    @Mock
    private TripService tripService;

    @Mock
    private CompanyService companyService;

    private CompanyController companyController;

    private TextField departure;
    private TextField destination;
    private TextField timeOfDeparture;
    private TextField tripType;
    private ListView<Bus> busListView;

    @BeforeAll
    static void initializeJavaFX() {
        if (!Platform.isFxApplicationThread()) {
            Platform.startup(() -> {});
            Platform.runLater(() -> {});
        }
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        departure = new TextField();
        destination = new TextField();
        timeOfDeparture = new TextField();
        tripType = new TextField();
        busListView = new ListView<>();

        companyController = new CompanyController();
        companyController.setCompanyService(companyService);
        companyController.setTripService(tripService);
        companyController.setDeparture(departure);
        companyController.setDestination(destination);
        companyController.setTimeOfDeparture(timeOfDeparture);
        companyController.setTripType(tripType);
        companyController.setBusListView(busListView);
    }

    @Test
    void testAddTrip_ValidInput_TripAdded() {
        Bus testBus = new Bus(companyController.getCurrentCompany());
        busListView.getItems().add(testBus);
        busListView.getSelectionModel().select(testBus);

        departure.setText("New York");
        destination.setText("Boston");
        timeOfDeparture.setText(LocalDate.now().plusDays(1).toString());
        tripType.setText("Express");

        Trip expectedTrip = new Trip();
        when(tripService.addTrip(any(Trip.class))).thenReturn(expectedTrip);

        companyController.addTrip();
        verify(tripService).addTrip(any(Trip.class));
    }

    @Test
    void testAddTrip_MissingFields_ThrowsAlert() {
        departure.setText("");
        destination.setText("Boston");
        timeOfDeparture.setText("");
        tripType.setText("Express");

        assertThrows(Exception.class, () -> companyController.addTrip());
    }

    @Test
    void testAddTrip_PastDate_ThrowsAlert() {
        Bus testBus = new Bus(companyController.getCurrentCompany());
        busListView.getItems().add(testBus);
        busListView.getSelectionModel().select(testBus);

        departure.setText("New York");
        destination.setText("Boston");
        timeOfDeparture.setText("2020 31 21");
        tripType.setText("Express");

        assertThrows(Exception.class, () -> companyController.addTrip());
    }
}
